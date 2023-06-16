package com.boldyrev.billmakertelegrambot.util;

import com.boldyrev.billmakertelegrambot.dto.BillDetails;
import com.boldyrev.billmakertelegrambot.dto.BillOwnerDetails;
import com.boldyrev.billmakertelegrambot.models.UserDetails;
import com.boldyrev.billmakertelegrambot.repositories.UserDetailsRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class CallbackQueryProcessor {

    public SendMessage process(CallbackQuery callbackQuery) {
        SendMessageBuilder response = SendMessage.builder().chatId(callbackQuery.getFrom().getId());
        String[] callBack = splitCallBackQuery(callbackQuery.getData());

        if (callBack.length == 2) {
            processShowingCallBack(response, callBack);
        } else if (callBack.length == 3) {
            processAddingCallBack(callbackQuery, response, callBack);
        }

        return response.build();
    }

    private SendMessageBuilder getOwnerDetailsPropertyMassage(SendMessageBuilder builder,
        String property) {
        final String INPUT = "Введите ";
        switch (property) {
            case "recipientCredentials":
                builder.text(INPUT + "реквизиты получателя");
                break;
            case "bankCredentials":
                builder.text(INPUT + "реквизиты банка");
                break;
            case "carrier":
                builder.text(INPUT + "данные перевозчика");
                break;
            case "signatory":
                builder.text(INPUT + "ФИО подписанта");
                break;
        }
        return builder;
    }

    private SendMessageBuilder getBillDetailsPropertyMassage(SendMessageBuilder builder,
        String property) {
        final String INPUT = "Введите ";
        switch (property) {
            case "billNumber":
                builder.text(INPUT + "номер счёта/акта");
                break;
            case "billDate":
                builder.text(INPUT + "дату в формате ДД.ММ.ГГГГ");
                builder.replyMarkup(formReplyKeyboard(
                    getDatesFromInterval(LocalDate.now().minusDays(1),
                        LocalDate.now().plusDays(4))));
                break;
            case "customer":
                builder.text(INPUT + "наименование организации покупателя");
                break;
            case "customerCredentials":
                builder.text(INPUT + "реквизиты покупателя");
                break;
            case "route":
                builder.text(INPUT + "маршрут");
                break;
            case "cost":
                builder.text(INPUT + "стоимость поездки");
                break;
            case "signed":
                builder.replyMarkup(formReplyKeyboard("Да", "Нет"));
                builder.text("Подписать документ? Да/Нет");
                break;
        }
        return builder;
    }

    private ReplyKeyboardMarkup formReplyKeyboard(String... values) {

        List<KeyboardRow> rows = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (i % 2 == 0) {
                KeyboardRow row = new KeyboardRow();
                rows.add(row);
            }
            rows.get(rows.size() - 1).add(values[i]);
        }

        return ReplyKeyboardMarkup.builder().oneTimeKeyboard(true).resizeKeyboard(true)
            .keyboard(rows).build();
    }

    private String[] getDatesFromInterval(LocalDate from, LocalDate to) {
        long daysCount = ChronoUnit.DAYS.between(from, to.plusDays(1));
        String[] dates = new String[(int) daysCount];

        for (int i = 0; i < daysCount; i++) {
            dates[i] = formatDate(from.plusDays(i));
        }

        return dates;
    }

    private void processAddingCallBack(CallbackQuery callbackQuery, SendMessageBuilder response,
        String[] callBack) {
        UserDetails details = UserDetailsRepository.STORAGE.getUserDetails(
            callbackQuery.getFrom());
        BillDetails billDetails = details.getBillDetails();
        BillOwnerDetails billOwnerDetails = details.getBillOwnerDetails();

        if (callBack[0].equals("/ownerdetails")) {
            billOwnerDetails.setProperty(callBack[1], callBack[2]);
            billDetails.setBillOwnerDetails(billOwnerDetails);
        } else if (callBack[0].equals("/billdetails")) {
            billDetails.setProperty(callBack[1], callBack[2]);
        }
        response.replyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build());
        response.text("Данные успешно добавлены");
    }

    private void processShowingCallBack(SendMessageBuilder response, String[] callBack) {
        if (callBack[0].equals("/ownerdetails")) {
            getOwnerDetailsPropertyMassage(response, callBack[1]);
        } else if (callBack[0].equals("/billdetails")) {
            getBillDetailsPropertyMassage(response, callBack[1]);
        }
    }

    private String[] splitCallBackQuery(String data) {
        String[] query = data.split("#");
        String[] systemQuery = query[0].split("\\s+");
        if (query.length == 1) {
            return systemQuery;
        } else {
            return new String[]{systemQuery[0], systemQuery[1], query[1]};
        }
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}
