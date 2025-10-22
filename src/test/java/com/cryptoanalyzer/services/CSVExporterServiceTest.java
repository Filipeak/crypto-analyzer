package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.StringBufferedWriterCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVExporterServiceTest {

    @Test
    void testOneFrameSave() {
        StringBufferedWriterCreator creator = new StringBufferedWriterCreator();
        CSVExporterService exporter = new CSVExporterService(creator);

        DataManager.getInstance().addObserver(exporter);
        DataManager.getInstance().initRepo();
        DataManager.getInstance().pushWebDataFrame(new WebDataFrame("BTC_USD", 1700000000, 10, 11, 20, 4, 3));
        DataManager.getInstance().flushRepo();

        final String expected = """
                symbol,timestamp,open,close,high,low,volume
                BTC_USD,1700000000,10.0,11.0,20.0,4.0,3.0
                """;

        assertEquals(expected, creator.getString());
    }
}