package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.data.WebDataSourceStatus;
import com.cryptoanalyzer.files.StringBufferedWriterCreator;
import com.cryptoanalyzer.sources.CSVDataLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceAnalysisServiceTest {

    @Test
    void testOneFrameSave() {
        StringBufferedWriterCreator creator = new StringBufferedWriterCreator();
        PriceAnalysisService exporter = new PriceAnalysisService(creator);

        DataManager.getInstance().addObserver(exporter);
        DataManager.getInstance().initRepo();

        CSVDataLoader loader = new CSVDataLoader("src/test/resources/testData.csv");
        WebDataSourceStatus status = loader.downloadFromWeb();

        assertEquals(WebDataSourceStatus.SUCCESS, status);

        DataManager.getInstance().flushRepo();
        DataManager.getInstance().removeObserver(exporter);

        final String expected = """
                - Total Change: -4.302332%
                - Average Daily Change: -0.12092516%
                - Max Daily Change: 4.2478056%
                - Monthly Volatility: 30474.25 = 28.235298%
                """;

        assertEquals(expected, creator.getString());
    }
}