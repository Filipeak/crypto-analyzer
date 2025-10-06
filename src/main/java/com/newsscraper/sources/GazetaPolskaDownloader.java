package com.newsscraper.sources;

import com.newsscraper.data.DataManager;
import com.newsscraper.data.WebDataFrame;
import com.newsscraper.data.WebSource;
import com.newsscraper.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GazetaPolskaDownloader implements WebSource {

    @Override
    public String getName() {
        return "Gazeta Polska";
    }

    @Override
    public void downloadFromWeb() {
        try {
            Document doc = Jsoup.connect("https://www.gazetapolska.pl/").get();
            Elements news = doc.select("div[role=article]");

            for (Element newsObj : news) {
                if (newsObj.selectFirst("div.post-patronaty") == null) {
                    String title = newsObj.selectFirst("h2.post-title").child(0).text();
                    String url = "https://www.gazetapolska.pl" + newsObj.selectFirst("h2.post-title").child(0).attr("href");
                    String imgUrl = newsObj.selectFirst("img").attr("src");

                    DataManager.getInstance().pushWebDataFrame(new WebDataFrame("gazetapolska.pl", title, url, imgUrl));
                } else if (newsObj.selectFirst("h2.post-title-patronaty") != null) {
                    String title = newsObj.selectFirst("h2.post-title-patronaty").child(0).text();
                    String url = "https://www.gazetapolska.pl" + newsObj.selectFirst("h2.post-title-patronaty").child(0).attr("href");
                    String imgUrl = newsObj.selectFirst("img").attr("src");

                    DataManager.getInstance().pushWebDataFrame(new WebDataFrame("gazetapolska.pl", title, url, imgUrl));
                } else if (newsObj.selectFirst("h2.post-title-sponsorowany") != null){
                    String title = newsObj.selectFirst("h2.post-title-sponsorowany").child(0).text();
                    String url = "https://www.gazetapolska.pl" + newsObj.selectFirst("h2.post-title-sponsorowany").child(0).attr("href");
                    String imgUrl = newsObj.selectFirst("img").attr("src");

                    DataManager.getInstance().pushWebDataFrame(new WebDataFrame("gazetapolska.pl", title, url, imgUrl));
                }
            }
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }
}
