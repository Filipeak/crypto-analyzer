package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.StringBufferedWriterCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONExporterServiceTest {

    @Test
    void testOneFrameSave() {
        StringBufferedWriterCreator creator = new StringBufferedWriterCreator();
        JSONExporterService exporter = new JSONExporterService(creator);

        DataManager.getInstance().addObserver(exporter);
        DataManager.getInstance().initRepo();
        DataManager.getInstance().pushWebDataFrame(new WebDataFrame("Binance", "BTC_USD", 1700000000, 10, 11, 20, 4, 3));
        DataManager.getInstance().flushRepo();
        DataManager.getInstance().removeObserver(exporter);

        final String expected = """
                [
                	{
                		"source": "Binance",
                		"symbol": "BTC_USD",
                		"timestamp": 1700000000,
                		"open": 10.0,
                		"close": 11.0,
                		"high": 20.0,
                		"low": 4.0,
                		"volume": 3.0
                	}
                ]
                """;

        assertEquals(expected, creator.getString());
    }
}