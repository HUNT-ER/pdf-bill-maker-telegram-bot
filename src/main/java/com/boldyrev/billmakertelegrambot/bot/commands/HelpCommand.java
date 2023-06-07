package com.boldyrev.billmakertelegrambot.bot.commands;

import com.boldyrev.billmakertelegrambot.bot.BotProperties;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends AbstractCommand {

    @Override
    public String getCommandIdentifier() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "������";
    }

    @Override
    public void processCommand(AbsSender absSender, Message message, String[] strings) {
        final String helMessage = "��� ������ ������� ������ � ��������� ����������� ���������� -> /ownerdetails.\n1) ��������� ����������. (������������ ����������� ��� ��, ���, ���)\n2) ��������� ����� (�������� �����, �����, ���, ���. ����, ���, ���, ����� �������� � ������)\n3) ���������� (������������ ����������� ��� ��, ���, ���, ����������� �����)\n4) ��������� (��� �������������� ����, �������� ������.�.�.)\n\n����� ��������� ������ ����������� ����� � ���� -> /billdetails.\n1) ����� (����� ���������, �������� 8)\n2) ���� (�������� ���� �� ������������ ���� ������� � ������� 01.05.2023)\n3) ���������� (������������ ����������� ����������, �������� ��� ����)\n4) ��������� ���������� (��������� ����������, ����, ���, ���, ����������� �����)\n5) ������� (��������, ��������� � �����)\n6) ��������� ��������� (��������� ��������� � ������, �������� 15000)\n7) ��������� (� ����������� �� ������ ��������� ����� � �������� ��� ���)\n\n����� ���������� ���� ����� ����� ������������ �������� -> /createbill.";
        sendTextMessage(absSender, message.getChatId(), helMessage);
    }
}
