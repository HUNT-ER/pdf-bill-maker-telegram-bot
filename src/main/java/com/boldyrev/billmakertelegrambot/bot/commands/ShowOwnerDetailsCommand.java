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
        builder.append("������ ��������� ����������:\n")
            .append("- ��������� ����������: ").append(
                details.getRecipientCredentials() == null ? "�� ���������"
                    : details.getRecipientCredentials())
            .append("\n")
            .append("- ��������� �����: ").append(
                details.getBankCredentials() == null ? "�� ���������" : details.getBankCredentials())
            .append("\n")
            .append("- ����������: ")
            .append(details.getCarrier() == null ? "�� ���������" : details.getCarrier())
            .append("\n")
            .append("- ���������: ")
            .append(details.getSignatory() == null ? "�� ���������" : details.getSignatory());

        sendTextMessage(absSender, message.getChatId(),
            builder.toString());
    }

    @Override
    public String getCommandIdentifier() {
        return "showownerdetails";
    }

    @Override
    public String getDescription() {
        return "�������� ������ ���������";
    }
}
