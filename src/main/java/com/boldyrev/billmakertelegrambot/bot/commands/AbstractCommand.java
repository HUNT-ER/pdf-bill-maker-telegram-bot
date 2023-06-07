package com.boldyrev.billmakertelegrambot.bot.commands;

import com.boldyrev.billmakertelegrambot.dto.BillDetails;
import com.boldyrev.billmakertelegrambot.dto.BillOwnerDetails;
import com.boldyrev.billmakertelegrambot.models.UserDetails;
import com.boldyrev.billmakertelegrambot.repositories.UserDetailsRepository;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class AbstractCommand implements IBotCommand {

    public void sendTextMessage(AbsSender absSender, long chatId, String message) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(message).build();
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(AbsSender absSender, SendMessage sendMessage) {
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        if (!UserDetailsRepository.STORAGE.containsUser(message.getFrom())) {
            UserDetails userDetails = new UserDetails(message.getFrom(), new BillOwnerDetails(), new BillDetails());
            UserDetailsRepository.STORAGE.save(userDetails);
        }
        processCommand(absSender, message, strings);
    }

    public abstract void processCommand(AbsSender absSender, Message message, String[] strings);

    protected InlineKeyboardButton createButton(String text, String data) {
        return InlineKeyboardButton.builder().text(text).callbackData(data).build();
    }

}
