package ru.liga.bot;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import static ru.liga.bot.buttons.Button.*;
import ru.liga.bot.command.HelpCommand;
import ru.liga.bot.command.StartCommand;
import ru.liga.bot.connection.ApiController;

import java.io.*;
import java.net.URISyntaxException;

public final class Bot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final ApiController apiController = new ApiController();

    public Bot(String botName, String botToken) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        register(new StartCommand("start", "Старт"));
        register(new HelpCommand("help", "Помощь"));
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Runnable runnable = () -> {
            try {
                messageHandler(update);
            } catch (IOException | URISyntaxException e) {
                System.out.println(e.getMessage());
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void messageHandler(Update update) throws IOException, URISyntaxException {
        String message = update.getMessage().getText();
        try {
            if (message.equals(CAT.getName())){
                execute(apiController.getPhoto(update.getMessage().getChatId().toString()));
            }else if (message.equals(JOKE.getName())){
                execute(apiController.getJoke(update.getMessage().getChatId().toString(), JOKE.getType()));
            }else if (message.equals(JOKE_FOR_ADULT.getName())){
                execute(apiController.getJoke(update.getMessage().getChatId().toString(), JOKE_FOR_ADULT.getType()));
            }else if (message.equals(TOAST.getName())){
                execute(apiController.getJoke(update.getMessage().getChatId().toString(), TOAST.getType()));
            }
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}
