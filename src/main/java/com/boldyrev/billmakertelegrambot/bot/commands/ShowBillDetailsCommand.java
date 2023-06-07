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
        builder.append("������ �����/����:\n")
            .append("- ����� ���������: ")
            .append(details.getBillNumber() == null ? "�� ���������" : details.getBillNumber())
            .append("\n")
            .append("- ���� ���������: ")
            .append(details.getBillDate() == null ? "�� ���������" : details.getBillDate().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy")))
            .append("\n")
            .append("- ����������: ")
            .append(details.getCustomer() == null ? "�� ���������" : details.getCustomer())
            .append("\n")
            .append("- ��������� ����������: ").append(
                details.getCustomerCredentials() == null ? "�� ���������"
                    : details.getCustomerCredentials())
            .append("\n")
            .append("- �������: ")
            .append(details.getRoute() == null ? "�� ���������" : details.getRoute())
            .append("\n")
            .append("- ��������� ���������: ")
            .append(details.getCost() == null ? "�� ���������" : details.getCost())
            .append("\n")
            .append("- � ��������: ").append(
                details.getSigned() == null ? "�� ���������" : details.getSigned() ? "��" : "���");

        sendTextMessage(absSender, message.getChatId(), builder.toString());
    }

    @Override
    public String getCommandIdentifier() {
        return "showbilldetails";
    }

    @Override
    public String getDescription() {
        return "�������� ������ �����/����";
    }
}
