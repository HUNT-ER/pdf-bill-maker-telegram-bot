package com.boldyrev.billmakertelegrambot;

import com.boldyrev.billmakertelegrambot.bot.BillMakerBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotStart {

    public static void main(String[] args) {
        BillMakerBot billMakerBot = new BillMakerBot();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(billMakerBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
