package com.newsscraper.exporters;

import com.newsscraper.data.DataManager;
import com.newsscraper.data.WebDataFrame;
import com.newsscraper.files.StringBufferedWriterCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XMLExporterTest {

    @Test
    void testOneFrameSave() {
        StringBufferedWriterCreator creator = new StringBufferedWriterCreator();
        XMLExporter exporter = new XMLExporter(creator);

        DataManager.getInstance().addObserver(exporter);
        DataManager.getInstance().initRepo();
        DataManager.getInstance().pushWebDataFrame(new WebDataFrame("BTC_USD", 1700000000, 10, 11, 20, 4, 3));
        DataManager.getInstance().flushRepo();

        final String expected = """
                <?xml version="1.0" encoding="utf-8"?>
                <frames>
                	<frame>
                		<symbol>BTC_USD</symbol>
                		<timestamp>1700000000</timestamp>
                		<open>10.0</open>
                		<close>11.0</close>
                		<high>20.0</high>
                		<low>4.0</low>
                		<volume>3.0</volume>
                	</frame>
                </frames>
                """;

        assertEquals(expected, creator.getString());
    }
}