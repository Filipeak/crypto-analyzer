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
            Elements parents = doc.select("div[data-st-area=section-news]");

            for (Element parent : parents) {
                Elements news = parent.select("a.wp-teaser-regular");

                for (Element newsObj : news) {
                    String url = newsObj.attr("href");
                    String imgUrl = newsObj.selectFirst("div.wp-image-placeholder").child(0).attr("src");
                    String title = newsObj.selectFirst("div.wp-teaser-title").text();

                    DataManager.getInstance().pushWebDataFrame(new WebDataFrame("wp.pl", title, url, imgUrl));
                }
            }
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }
}
