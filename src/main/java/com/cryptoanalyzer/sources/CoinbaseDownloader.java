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
        Instant start = end.minusSeconds(24 * 60 * 60);
        String jsonStr = HTTPDownloadUtils.fetch(String.format("https://api.exchange.coinbase.com/products/BTC-USD/candles?granularity=3600&start=%s&end=%s", start, end));

        if (jsonStr == null) {
            return WebSourceStatus.FAILURE_NO_INTERNET;
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonStr);

            for (int i = jsonArray.length() - 1; i >= 0; i--) {
                JSONArray element = jsonArray.getJSONArray(i);

                DataManager.getInstance().pushWebDataFrame(new WebDataFrame(
                        "BTC_USD",
                        element.getInt(0),
                        element.getFloat(3),
                        element.getFloat(4),
                        element.getFloat(2),
                        element.getFloat(1),
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
