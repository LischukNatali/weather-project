package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//парсер сайта гисметео
public class Gismeteo {
    private static Document getPage() throws IOException {
        String url = "https://www.gismeteo.ua/weather-dnipro-5077/10-days/";
        Document page = Jsoup.parse(new URL(url), 10000);
        return page;
    }

    public static void main(String[] args) throws IOException {
        Document page = getPage();
        Element tableWth = page.select("div[class=widget__container]").first();
        Elements dates = page.select("span[class=w_date__date black]");
        Elements temp = page.select("span[class=unit unit_temperature_c]");
        Map<String, String> map = new HashMap<>();
        int index = 0;
        int max = 10;

        for (Element el: dates) {
            if (index >= 10) break;
            map.put(el.text(), temp.get(index).text());
            index++;
        }
        System.out.println(map);
    }
}
