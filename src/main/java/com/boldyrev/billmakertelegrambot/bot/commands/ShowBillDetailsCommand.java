package com.boldyrev.billmakertelegrambot.bot.commands;

import com.boldyrev.billmakertelegrambot.dto.BillDetails;
import com.boldyrev.billmakertelegrambot.repositories.UserDetailsRepository;
import java.time.format.DateTimeFormatter;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ShowBillDetailsCommand extends AbstractCommand {

    @Override
    public void processCommand(AbsSender absSender, Message message, String[] strings) {
        BillDetails details = UserDetailsRepository.STORAGE.getUserDetails(message.getFrom())
            .getBillDetails();
        StringBuilder builder = new StringBuilder();
        builder.append("Детали счёта/акта:\n")
            .append("- Номер документа: ")
            .append(details.getBillNumber() == null ? "не заполнено" : details.getBillNumber())
            .append("\n")
            .append("- Дата документа: ")
            .append(details.getBillDate() == null ? "не заполнено" : details.getBillDate().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy")))
            .append("\n")
            .append("- Покупатель: ")
            .append(details.getCustomer() == null ? "не заполнено" : details.getCustomer())
            .append("\n")
            .append("- Реквизиты покупателя: ").append(
                details.getCustomerCredentials() == null ? "не заполнено"
                    : details.getCustomerCredentials())
            .append("\n")
            .append("- Маршрут: ")
            .append(details.getRoute() == null ? "не заполнено" : details.getRoute())
            .append("\n")
            .append("- Стоимость перевозки: ")
            .append(details.getCost() == null ? "не заполнено" : details.getCost())
            .append("\n")
            .append("- С подписью: ").append(
                details.getSigned() == null ? "не заполнено" : details.getSigned() ? "Да" : "Нет");

        sendTextMessage(absSender, message.getChatId(), builder.toString());
    }

    @Override
    public String getCommandIdentifier() {
        return "showbilldetails";
    }

    @Override
    public String getDescription() {
        return "Показать детали счёта/акта";
    }
}
