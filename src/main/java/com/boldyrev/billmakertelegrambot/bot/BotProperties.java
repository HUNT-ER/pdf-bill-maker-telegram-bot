package com.boldyrev.billmakertelegrambot.bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum BotProperties {
    PROPERTIES;
    private String username;
    private String token;

    BotProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/bot_config.properties"));
            username = properties.getProperty("telegram.bot.username");
            token = properties.getProperty("telegram.bot.token");
        } catch (IOException e) {
            throw new RuntimeException("Properties file not found");
        }
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

}
