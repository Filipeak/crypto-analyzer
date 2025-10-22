package com.cryptoanalyzer.sources;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.data.WebDataSource;
import com.cryptoanalyzer.data.WebDataSourceStatus;
import com.cryptoanalyzer.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;

public class BinanceDownloader implements WebDataSource {

    @Override
    public String getName() {
        return "Binance";
    }


    // REF: https://developers.binance.com/docs/derivatives/usds-margined-futures/market-data/rest-api/Kline-Candlestick-Data
    @Override
    public WebDataSourceStatus downloadFromWeb() {
        String jsonStr = HTTPDownloadUtils.fetch("https://api.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1h&limit=720");

        if (jsonStr == null) {
            return WebDataSourceStatus.FAILURE_NO_INTERNET;
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonStr);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray element = jsonArray.getJSONArray(i);

                DataManager.getInstance().pushWebDataFrame(new WebDataFrame(
                        "Binance",
                        "BTC_USD",
                        (int)(element.getLong(0) / 1000),
                        element.getFloat(1),
                        element.getFloat(4),
                        element.getFloat(2),
                        element.getFloat(3),
                        element.getFloat(5)
                ));
            }

            return WebDataSourceStatus.SUCCESS;
        } catch (JSONException e) {
            Logger.error(e.getMessage());

            return WebDataSourceStatus.FAILURE_NO_DATA;
        }
    }
}
