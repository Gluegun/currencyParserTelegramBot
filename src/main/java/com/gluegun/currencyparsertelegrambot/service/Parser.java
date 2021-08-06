package com.gluegun.currencyparsertelegrambot.service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Parser {

    public double getCurrencyValue(String currencyName) {
        String sourceUrl = "https://www.cbr.ru/currency_base/daily/";
        Document document = null;
        try {
            document = Jsoup.connect(sourceUrl).maxBodySize(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements table = document.select("table.data").select("tr");

        double currency = 0.0;

        for (Element element : table) {
            if (element.text().contains(currencyName)) {
                Elements tds = element.select("td");
                String currencyValue = tds.get(tds.size() - 1).text().replaceAll(",", ".");
                currency = Double.parseDouble(currencyValue);
                break;
            }
        }
        return currency;
    }


}
