package com.gluegun.currencyparsertelegrambot.controller;

import com.gluegun.currencyparsertelegrambot.service.Parser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DefaultController {

    private final Parser parser;

    @GetMapping
    public ResponseEntity<Double> getValueForCurrency(@RequestParam("currency") String currency) {
        double currencyValue = parser.getCurrencyValue(currency);
        return new ResponseEntity<>(currencyValue, HttpStatus.OK);
    }
}
