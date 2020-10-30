package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PogodaSpb {
    private static Document getPage() throws IOException {
        String url = "https://www.pogoda.spb.ru/";
        Document page = Jsoup.parse(new URL(url), 5000);
        return page;
    }

    //29.10 Четверг погода сегодня
    //29.10
    //\d{2}\.\d{2}
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
    private static String geDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("cant extract date from string!");
    }

    private static int printPartValues(Elements values, int index) {
        int iterationCount = 4;
        if (index == 0) {
            Element valLn = values.get(3);
            boolean isMorning = valLn.text().contains("Утро");
            if (isMorning) {
                iterationCount = 3;
            }
        }
            for (int i = 0; i < iterationCount; i++) {
                Element valLine = values.get(index + i);
                for (Element td : valLine.select("td")) {
                    System.out.print(td.text() + "          ");
                }
                System.out.println();
            }
        return iterationCount;
    }
    public static void main(String[] args) throws Exception {

        Document page = getPage();
        Element tableW = page.select("table[class=wt]").first();
        Elements names = tableW.select("tr[class=wth]");
        Elements values = tableW.select("tr[valign=top]");
        int index = 0;


        for (Element name: names) {
            String dateString = name.select("th[id=dt]").text();
            String date = geDateFromString(dateString);
            System.out.println(date + "  Явление   Температура  Давление Влажность  Ветер  ");
            int iterationCount = printPartValues(values, index);
            index = iterationCount + index;
        }

    }
}
