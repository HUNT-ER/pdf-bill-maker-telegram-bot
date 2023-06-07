package com.boldyrev.billmakertelegrambot.bot.commands;

import com.boldyrev.billmakertelegrambot.dto.BillDetails;
import com.boldyrev.billmakertelegrambot.dto.BillOwnerDetails;
import com.boldyrev.billmakertelegrambot.models.UserDetails;
import com.boldyrev.billmakertelegrambot.repositories.UserDetailsRepository;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends AbstractCommand {

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Старт";
    }

    @Override
    public void processCommand(AbsSender absSender, Message message, String[] strings) {
        if (!UserDetailsRepository.STORAGE.containsUser(message.getFrom())) {
            UserDetails userDetails = new UserDetails(message.getFrom(), new BillOwnerDetails(),
                new BillDetails());
            UserDetailsRepository.STORAGE.save(userDetails);
        }
        sendTextMessage(absSender, message.getChatId(),
            "Для начала давайте ознакомимся с инструкцией, нажмите /help.");
    }
}
