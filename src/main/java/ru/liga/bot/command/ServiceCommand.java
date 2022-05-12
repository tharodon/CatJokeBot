package ru.liga.bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import static ru.liga.bot.buttons.Button.*;
import ru.liga.bot.keyboard.ReplyKeyboardMaker;

import java.util.Arrays;
import java.util.Collections;

public abstract class ServiceCommand extends BotCommand {
    private Logger logger = LoggerFactory.getLogger(ServiceCommand.class);

    ServiceCommand(String identifier, String description) {
        super(identifier, description);
    }

    /**
     * Отправка ответа пользователю
     */
    void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text) {
        System.out.println("sendAnswer called");
        SendMessage message = new SendMessage();
        //включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        message.enableMarkdown(true);
        message.setReplyMarkup(new ReplyKeyboardMaker().getKeyboard(Arrays.asList(CAT.getName(), JOKE.getName(), JOKE_FOR_ADULT.getName())));
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            absSender.execute(message);
        } catch (TelegramApiException ignore) {
        }
    }

    public abstract void execute(AbsSender absSender, User user, Chat chat, String[] strings);
}
