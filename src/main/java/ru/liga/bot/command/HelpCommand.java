package ru.liga.bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends ServiceCommand{

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
            String userName = (user.getUserName() != null) ? user.getUserName() :
                    String.format("%s %s", user.getLastName(), user.getFirstName());
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                    "Нажми на \"Тык\" и ты узнаешь" +
                            " какой ты кот сегодня");
    }
}
