package com.gluegun.currencyparsertelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CurrencyParserTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyParserTelegramBotApplication.class, args);
    }

}
