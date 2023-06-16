package com.boldyrev.billmakertelegrambot;

import com.boldyrev.billmakertelegrambot.bot.BillMakerBot;
import com.boldyrev.billmakertelegrambot.bot.BotProperties;
import com.boldyrev.billmakertelegrambot.exceptions.IncorrectBotPropertiesException;
import java.util.Arrays;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotStart {

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IncorrectBotPropertiesException("Bot properties in command line is invalid");
        }
        BotProperties.PROPERTIES.setUsername(args[0]);
        BotProperties.PROPERTIES.setToken(args[1]);

        BillMakerBot billMakerBot = new BillMakerBot();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(billMakerBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e.getMessage() + "\n" + "Check command line arguments");
        }
    }
}
