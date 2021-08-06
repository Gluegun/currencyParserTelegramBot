package com.gluegun.currencyparsertelegrambot.service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    private final Parser parser;

    @Value("${telegram.api.token}")
    private String botToken;

    @Value("${telegram.bot.name}")
    private String botName;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            String userName = message.getFrom().getUserName();
            String responseText = "";
            if (message.hasText()) {
                String messageText = message.getText();
                if (messageText.equals("/start")) {
                    responseText = "Welcome, " + (userName == null || userName.isEmpty() ? "user" : userName);
                    log.info("{} asked starts bot", message.getFrom());
                    sentNotification(chatId, responseText);
                }
            }
        }
    }

    private void sentNotification(long chatId, String responseText) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), responseText);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 10 * * *", zone = "Europe/Moscow")
    public void sendDailyMessage() {
        String message = "Курс евро на сегодня " + parser.getCurrencyValue("Евро");
        SendMessage sendMessage = new SendMessage("373103052", message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
