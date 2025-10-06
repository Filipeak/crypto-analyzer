package com.newsscraper.sources;

import com.newsscraper.data.DataManager;
import com.newsscraper.data.WebDataFrame;
import com.newsscraper.data.WebSource;
import com.newsscraper.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WirtualnaPolskaDownloader implements WebSource {

    @Override
    public String getName() {
        return "Wirtualna Polska";
    }

    @Override
    public void downloadFromWeb() {
        try {
            Document doc = Jsoup.connect("https://www.wp.pl/").get();
            doc.select("div[data-bdc=section-mmoGames]").remove();

            Elements news = doc.select("div.wp-teaser-content");
            Elements additionalNews = doc.select("div.wp-content-wrapper");

            news.addAll(additionalNews);

            for (Element e : news) {
                String title = e.text();
                String url = e.parent().attr("href");
                String imgUrl = e.parent().select("img").attr("src");

                DataManager.getInstance().pushWebDataFrame(new WebDataFrame("wp.pl", title, url, imgUrl));
            }
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }
}
