package com.boldyrev.billmakertelegrambot.bot.commands;

import com.boldyrev.billmakertelegrambot.models.UserDetails;
import com.boldyrev.billmakertelegrambot.repositories.UserDetailsRepository;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ClearBillCommand extends AbstractCommand {

    @Override
    public void processCommand(AbsSender absSender, Message message, String[] strings) {
        UserDetails userDetails = UserDetailsRepository.STORAGE.getUserDetails(message.getFrom());
        userDetails.getBillDetails().resetDetails();

        sendTextMessage(absSender, message.getChatId(), "Данные последнего счёт/акта очищены");
    }

    @Override
    public String getCommandIdentifier() {
        return "clearbill";
    }

    @Override
    public String getDescription() {
        return "Очистить данные счёта/акта";
    }
}
