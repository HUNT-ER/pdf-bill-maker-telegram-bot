package com.boldyrev.billmakertelegrambot.bot;

import com.boldyrev.billmakertelegrambot.util.CallbackQueryProcessor;
import com.boldyrev.billmakertelegrambot.util.CommandsRegistrar;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BillMakerBot extends TelegramLongPollingCommandBot {

    private BotProperties botProperties;
    private CommandsRegistrar commandsRegistrar;
    private CallbackQueryProcessor callbackQueryProcessor;

    private Map<Long, CallbackQuery> lastCallbackMessage;

    public BillMakerBot() {
        super();
        botProperties = BotProperties.PROPERTIES;
        commandsRegistrar = new CommandsRegistrar(this);
        commandsRegistrar.registerCommands();
        callbackQueryProcessor = new CallbackQueryProcessor();
        lastCallbackMessage = new HashMap<>();
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }

        Message message = update.getMessage();
        if (message != null) {
            User user = message.getFrom();
            if (lastCallbackMessage.containsKey(user.getId()) && lastCallbackMessage.get(user.getId()) != null) {
                CallbackQuery callback = lastCallbackMessage.get(user.getId());
                callback.setData(callback.getData() + "#" + message.getText());

                SendMessage callBackMessage = callbackQueryProcessor.process(callback);
                sendMessage(callBackMessage);

                lastCallbackMessage.put(user.getId(), null);
            }
        }

    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        Map<String, String> registeredCommands = getRegisteredCommands().stream().collect(
            Collectors.toMap(command -> "/" + command.getCommandIdentifier(),
                IBotCommand::getDescription));
        StringBuilder builder = new StringBuilder();
        builder.append("Список доступных комманд:\n");
        registeredCommands.forEach(
            (k, v) -> builder.append(k).append(" - ").append(v).append("\n"));
        sendMessage(update.getMessage().getChatId(), builder.toString());
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = SendMessage.builder().chatId(chatId).text(text).build();
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        lastCallbackMessage.put(callbackQuery.getFrom().getId(), callbackQuery);
        SendMessage message = callbackQueryProcessor.process(callbackQuery);
        sendMessage(message);
    }

}
