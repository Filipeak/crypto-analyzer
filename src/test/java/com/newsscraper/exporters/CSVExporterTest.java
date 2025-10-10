package com.newsscraper.exporters;

import com.newsscraper.data.DataManager;
import com.newsscraper.data.WebDataFrame;
import com.newsscraper.files.StringBufferedWriterCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVExporterTest {

    @Test
    void onSetDataTest() {
        StringBufferedWriterCreator creator = new StringBufferedWriterCreator();
        CSVExporter exporter = new CSVExporter(creator);

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