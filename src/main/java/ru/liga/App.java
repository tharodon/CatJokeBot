package ru.liga;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.bot.Bot;

/**
 * Hello world!
 *
 */


public class App {
    public static void main( String[] args ){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot("WhatCatAreYouTodayBot", "5214655246:AAF_JsM9l6ot1tqrVWh4SaePo57wf-PO998");
            botsApi.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
