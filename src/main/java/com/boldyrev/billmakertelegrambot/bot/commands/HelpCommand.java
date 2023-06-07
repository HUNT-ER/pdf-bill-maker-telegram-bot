package com.boldyrev.billmakertelegrambot.bot.commands;

import com.boldyrev.billmakertelegrambot.bot.BotProperties;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends AbstractCommand {

    @Override
    public String getCommandIdentifier() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Помощь";
    }

    @Override
    public void processCommand(AbsSender absSender, Message message, String[] strings) {
        final String helMessage = "Для начала введите данные о владельце формируемых документов -> /ownerdetails.\n1) Реквизиты получателя. (Наименование организации или ИП, ИНН, КПП)\n2) Реквизиты банка (Название банка, адрес, БИК, Кор. счет, ИНН, КПП, номер договора с банком)\n3) Перевозчик (Наименование организации или ИП, ИНН, КПП, Юридический адрес)\n4) Подписант (ФИО подписывающего лица, например Иванов.И.И.)\n\nЗатем заполните детали формируемых счёта и акта -> /billdetails.\n1) Номер (Номер документа, например 8)\n2) Дата (Выберите дату из предложенной либо введите в формате 01.05.2023)\n3) Покупатель (Наименование организации покупателя, например ООО «Май»)\n4) Реквизиты покупателя (Реквизиты покупателя, ОГРН, ИНН, КПП, Юридический адрес)\n5) Маршрут (Например, Краснодар – Анапа)\n6) Стоимость перевозки (Стоимость перевозки в рублях, например 15000)\n7) Подписать (В зависимости от выбора документы будут с подписью или без)\n\nПосле заполнения всех полей можно сформировать документ -> /createbill.";
        sendTextMessage(absSender, message.getChatId(), helMessage);
    }
}
