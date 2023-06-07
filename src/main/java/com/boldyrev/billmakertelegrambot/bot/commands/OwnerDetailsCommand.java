package com.boldyrev.billmakertelegrambot.bot.commands;

import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class OwnerDetailsCommand extends AbstractCommand {

    @Override
    public String getCommandIdentifier() {
        return "ownerdetails";
    }

    @Override
    public String getDescription() {
        return "Заполнить данные владельца";
    }

    @Override
    public void processCommand(AbsSender absSender, Message message, String[] strings) {

        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
            .keyboardRow(
                List.of(createButton("Реквизиты получателя", "/ownerdetails recipientCredentials"),
                    createButton("Реквизиты банка", "/ownerdetails bankCredentials")))
            .keyboardRow(List.of(createButton("Перевозчик", "/ownerdetails carrier"),
                createButton("Подписант", "/ownerdetails signatory")))
            .build();

        SendMessage sendMessage = SendMessage.builder().chatId(message.getChatId())
            .text("Заполните информацию владельца счёт/акта").replyMarkup(markup).build();

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
