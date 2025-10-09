package com.newsscraper.sources;

import com.newsscraper.data.DataManager;
import com.newsscraper.data.WebDataFrame;
import com.newsscraper.data.WebSource;
import com.newsscraper.data.WebSourceStatus;
import com.newsscraper.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.json.*;

public class BinanceDownloader implements WebSource {

    @Override
    public String getName() {
        return "Binance";
    }

    @Override
    public WebSourceStatus downloadFromWeb() {
        URL url;

        try {
            url = URI.create("https://api.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1h&limit=24").toURL();
        } catch (MalformedURLException e) {
            Logger.error(e.getMessage());

            return WebSourceStatus.FAILURE_NO_DATA;
        }

        HttpURLConnection conn;

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.getResponseCode();
        } catch (IOException e) {
            Logger.error(e.getMessage());

            return WebSourceStatus.FAILURE_NO_INTERNET;
        }

        StringBuilder jsonStr = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }
        } catch (Exception e) {
            Logger.error(e.getMessage());

            return WebSourceStatus.FAILURE_NO_DATA;
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonStr.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray element = jsonArray.getJSONArray(i);

                DataManager.getInstance().pushWebDataFrame(new WebDataFrame(
                        "BTC_USD",
                        element.getLong(0),
                        element.getFloat(1),
                        element.getFloat(4),
                        element.getFloat(3),
                        element.getFloat(2),
                        element.getFloat(5)
                ));
            }

            return WebSourceStatus.SUCCESS;
        } catch (JSONException e) {
            Logger.error(e.getMessage());

            return WebSourceStatus.FAILURE_NO_DATA;
        }
    }
}
