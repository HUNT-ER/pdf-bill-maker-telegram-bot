package com.boldyrev.billmakertelegrambot.bot.commands;

import com.boldyrev.billmakertelegrambot.dto.BillOwnerDetails;
import com.boldyrev.billmakertelegrambot.repositories.UserDetailsRepository;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ShowOwnerDetailsCommand extends AbstractCommand {

    @Override
    public void processCommand(AbsSender absSender, Message message, String[] strings) {
        BillOwnerDetails details = UserDetailsRepository.STORAGE.getUserDetails(message.getFrom())
            .getBillOwnerDetails();
        StringBuilder builder = new StringBuilder();
        builder.append("Данные владельца документов:\n")
            .append("- Реквизиты получателя: ").append(
                details.getRecipientCredentials() == null ? "не заполнено"
                    : details.getRecipientCredentials())
            .append("\n")
            .append("- Реквизиты банка: ").append(
                details.getBankCredentials() == null ? "не заполнено" : details.getBankCredentials())
            .append("\n")
            .append("- Перевозчик: ")
            .append(details.getCarrier() == null ? "не заполнено" : details.getCarrier())
            .append("\n")
            .append("- Подписант: ")
            .append(details.getSignatory() == null ? "не заполнено" : details.getSignatory());

        sendTextMessage(absSender, message.getChatId(),
            builder.toString());
    }

    @Override
    public String getCommandIdentifier() {
        return "showownerdetails";
    }

    @Override
    public String getDescription() {
        return "Показать данные владельца";
    }
}
