package com.newsscraper.sources;

import com.newsscraper.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public final class CryptoHTTPDownloadUtils {

    public static String fetch(String urlString) {
        URL url;

        try {
            url = URI.create(urlString).toURL();
        } catch (MalformedURLException e) {
            Logger.error(e.getMessage());

            return null;
        }

        HttpURLConnection conn;

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
        } catch (IOException e) {
            Logger.error(e.getMessage());

            return null;
        }

        StringBuilder jsonStr = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }
        } catch (Exception e) {
            Logger.error(e.getMessage());

            return null;
        }

        return jsonStr.toString();
    }
}
