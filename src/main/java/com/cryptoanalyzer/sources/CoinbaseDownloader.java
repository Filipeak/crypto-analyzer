package com.cryptoanalyzer.sources;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.data.WebSource;
import com.cryptoanalyzer.data.WebSourceStatus;
import com.cryptoanalyzer.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import java.time.Instant;

public class CoinbaseDownloader implements WebSource {

    @Override
    public String getName() {
        return "Coinbase";
    }


    // REF: https://docs.cdp.coinbase.com/api-reference/exchange-api/rest-api/products/get-product-candles
    @Override
    public WebSourceStatus downloadFromWeb() {
        Instant end = Instant.now();
        Instant start = end.minusSeconds(10 * 24 * 60 * 60);
        WebDataFrame[] reversedData = new WebDataFrame[720];
        int currentIndex = 0;

        for (int part = 0; part < 3; part++) {
            String jsonStr = HTTPDownloadUtils.fetch(String.format("https://api.exchange.coinbase.com/products/BTC-USD/candles?granularity=3600&start=%s&end=%s", start, end));
            end = start;
            start = end.minusSeconds(10 * 24 * 60 * 60);

            if (jsonStr == null) {
                return WebSourceStatus.FAILURE_NO_INTERNET;
            }

            try {
                JSONArray jsonArray = new JSONArray(jsonStr);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONArray element = jsonArray.getJSONArray(i);

                    reversedData[currentIndex++] = new WebDataFrame(
                            "BTC_USD",
                            element.getInt(0),
                            element.getFloat(3),
                            element.getFloat(4),
                            element.getFloat(2),
                            element.getFloat(1),
                            element.getFloat(5)
                    );
                }
            } catch (JSONException e) {
                Logger.error(e.getMessage());

                return WebSourceStatus.FAILURE_NO_DATA;
            }
        }

        for (int i = reversedData.length - 1; i >= 0; i--) {
            DataManager.getInstance().pushWebDataFrame(reversedData[i]);
        }

        return WebSourceStatus.SUCCESS;
    }
}
