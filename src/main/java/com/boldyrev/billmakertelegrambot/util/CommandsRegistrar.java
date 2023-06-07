package com.boldyrev.billmakertelegrambot.util;

import com.boldyrev.billmakertelegrambot.bot.commands.ClearBillCommand;
import com.boldyrev.billmakertelegrambot.bot.commands.CreateBillCommand;
import com.boldyrev.billmakertelegrambot.bot.commands.OwnerDetailsCommand;
import com.boldyrev.billmakertelegrambot.bot.commands.BillDetailsCommand;
import com.boldyrev.billmakertelegrambot.bot.commands.HelpCommand;
import com.boldyrev.billmakertelegrambot.bot.commands.ShowBillDetailsCommand;
import com.boldyrev.billmakertelegrambot.bot.commands.ShowOwnerDetailsCommand;
import com.boldyrev.billmakertelegrambot.bot.commands.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;

public class CommandsRegistrar {

    private TelegramLongPollingCommandBot bot;

    public CommandsRegistrar(TelegramLongPollingCommandBot bot) {
        this.bot = bot;
    }

    public void registerCommands() {
        bot.register(new BillDetailsCommand());
        bot.register(new StartCommand());
        bot.register(new HelpCommand());
        bot.register(new OwnerDetailsCommand());
        bot.register(new CreateBillCommand());
        bot.register(new ShowBillDetailsCommand());
        bot.register(new ShowOwnerDetailsCommand());
        bot.register(new ClearBillCommand());
    }

}
