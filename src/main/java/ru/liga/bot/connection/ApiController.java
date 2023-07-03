package ru.liga.bot.connection;

import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.bot.keyboard.ReplyKeyboardMaker;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import static ru.liga.bot.buttons.Button.*;

public class ApiController {

    private static final String URL_CAT = "https://api.thecatapi.com/v1/images/search";
    private static final String URL_JOKE = "http://rzhunemogu.ru/RandJSON.aspx?CType=";
    private static final String KEY_URL = "url";

    private static final String KEY_CONTENT = "content";

    public SendMessage getJoke(String chatId, Long typeOfMessage) throws IOException {
        String response = requestJoke(URL_JOKE + typeOfMessage);
        SendMessage sendMessage = new SendMessage(chatId, response);
        sendMessage.setReplyMarkup(new ReplyKeyboardMaker().getKeyboard(Arrays.asList(CAT.getName(),
                JOKE.getName(),
                JOKE_FOR_ADULT.getName(),
                TOAST.getName())));
        return sendMessage;
    }

    public SendPhoto getPhoto(String chatId) throws IOException {
        StringBuilder responseStrBuilder = requestApi(URL_CAT);
        JSONObject jsonObject = new JSONObject(responseStrBuilder.substring(1, responseStrBuilder.length() - 1));
        SendPhoto sendPhoto = requestImage(jsonObject.getString(KEY_URL));
        sendPhoto.setChatId(chatId);
        sendPhoto.setReplyMarkup(new ReplyKeyboardMaker().getKeyboard(Arrays.asList(CAT.getName(),
                JOKE.getName(),
                JOKE_FOR_ADULT.getName(),
                TOAST.getName())));
        return sendPhoto;
    }

    private SendPhoto requestImage(String url) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(url);

        InputStream inputStream = new ByteArrayInputStream(connection.getInputStream().readAllBytes());
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(inputStream, "picture.jpg"));
        connection.disconnect();
        return sendPhoto;
    }

    private String requestJoke(String url) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(url);
        byte[] bytes = connection.getInputStream().readAllBytes();
        String s = new String(bytes, "Cp1251");
        return s.substring(12, s.length() - 2);
    }

    private StringBuilder requestApi(String url) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(url);

        InputStream inputStreamObject = connection.getInputStream();
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStreamObject));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        connection.disconnect();
        return responseStrBuilder;
    }

    private HttpURLConnection getHttpURLConnection(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        return connection;
    }
}
