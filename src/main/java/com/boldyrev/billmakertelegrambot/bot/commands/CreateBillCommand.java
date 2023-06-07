package com.boldyrev.billmakertelegrambot.bot.commands;

import com.boldyrev.billmakertelegrambot.dto.BillDTO;
import com.boldyrev.billmakertelegrambot.models.UserDetails;
import com.boldyrev.billmakertelegrambot.repositories.UserDetailsRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CreateBillCommand extends AbstractCommand {

    @Override
    public String getCommandIdentifier() {
        return "createbill";
    }

    @Override
    public String getDescription() {
        return "Сформировать счёт/акт";
    }

    @Override
    public void processCommand(AbsSender absSender, Message message, String[] strings) {

        UserDetails userDetails = UserDetailsRepository.STORAGE.getUserDetails(message.getFrom());

        if (!userDetails.getBillOwnerDetails().isCorrect()) {
            sendTextMessage(absSender, message.getChatId(), "Заполните полностью данные владельца");
            return;
        } else if (!userDetails.getBillDetails().isCorrect()) {
            sendTextMessage(absSender, message.getChatId(),
                "Заполните полностью данные счёта/акта");
            return;
        }

        String response = getBill(userDetails);

        sendTextMessage(absSender, message.getChatId(), response);
    }

    private String getBill(UserDetails userDetails) {
        RestTemplate restTemplate = new RestTemplate();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/rest_service_url.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Properties file with URL not found");
        }

        try {
            BillDTO billDTO = restTemplate.postForObject(
                properties.getProperty("bill.service.url.new"),
                userDetails.getBillDetails(), BillDTO.class);
            return formBillOutput(billDTO);
        } catch (HttpClientErrorException e) {
            return parseResponseError(e.getMessage());
        }
    }

    private String formBillOutput(BillDTO billDTO) {
        StringBuilder builder = new StringBuilder();
        builder.append("Счёт/акт №").append(billDTO.getBillNumber())
            .append(" успешно сформирован")
            .append("\n").append("Дата: ")
            .append(billDTO.getBillDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
            .append("\n").append("Компания: ").append(billDTO.getCustomer()).append("\n")
            .append("Маршрут: ").append(billDTO.getRoute()).append("\n").append("Стоимость: ")
            .append(billDTO.getCost()).append("\n").append("Ссылка на скачивание документа: ")
            .append(billDTO.getUrl());
        return builder.toString();
    }

    private String parseResponseError(String error) {
        Pattern pattern = Pattern.compile("(\\w+\\s-\\s)([а-яА-Я0-9ёЁ ]+)");
        Matcher matcher = pattern.matcher(error);
        StringBuilder builder = new StringBuilder();
        builder.append("Ошибка формирования документа:\n");
        while (matcher.find()) {
            builder.append("- ").append(matcher.group(2)).append("\n");
        }
        return builder.toString();
    }

}
