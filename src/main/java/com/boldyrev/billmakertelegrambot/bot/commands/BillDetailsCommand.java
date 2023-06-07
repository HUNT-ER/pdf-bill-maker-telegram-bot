package com.boldyrev.billmakertelegrambot.bot.commands;

import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BillDetailsCommand extends AbstractCommand {
    @Override
    public String getCommandIdentifier() {
        return "billdetails";
    }

    @Override
    public String getDescription() {
        return "Заполнить новый счёт/акт";
    }

    @Override
    public void processCommand(AbsSender absSender, Message message, String[] strings) {

        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(createButton("Номер", "/billdetails billNumber"), createButton("Дата", "/billdetails billDate")))
            .keyboardRow(List.of(createButton("Покупатель", "/billdetails customer"), createButton("Реквизиты покупателя", "/billdetails customerCredentials")))
            .keyboardRow(List.of(createButton("Маршрут", "/billdetails route"), createButton("Стоимость перевозки", "/billdetails cost")))
            .keyboardRow(List.of(createButton("Подписать?", "/billdetails signed")))
            .build();

        SendMessage sendMessage = SendMessage.builder().chatId(message.getChatId())
            .text("Заполните детали чека").replyMarkup(markup).build();

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
