package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.StringBufferedWriterCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceAnalysisServiceTest {

    @Test
    void testOneFrameSave() {
        StringBufferedWriterCreator creator = new StringBufferedWriterCreator();
        PriceAnalysisService exporter = new PriceAnalysisService(creator);

        DataManager.getInstance().addObserver(exporter);
        DataManager.getInstance().initRepo();
        DataManager.getInstance().pushWebDataFrame(new WebDataFrame("Binance", "BTC_USD", 1700000000, 10, 11, 20, 4, 3));
        DataManager.getInstance().pushWebDataFrame(new WebDataFrame("Binance", "BTC_USD", 1700000000, 10, 13, 20, 4, 3));
        DataManager.getInstance().pushWebDataFrame(new WebDataFrame("Binance", "BTC_USD", 1700000000, 10, 15, 20, 4, 3));
        DataManager.getInstance().flushRepo();

        final String expected = """
                - Total Change: 50.0%
                - Average Change: 29.999998%
                - Max Change: 50.0%
                - Monthly Volatility: 0.57735026 = 3.8490016%
                """;

        assertEquals(expected, creator.getString());
    }
}