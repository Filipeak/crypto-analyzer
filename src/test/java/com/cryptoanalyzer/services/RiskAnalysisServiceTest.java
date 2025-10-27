package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataSourceStatus;
import com.cryptoanalyzer.files.StringBufferedWriterCreator;
import com.cryptoanalyzer.sources.CSVDataLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RiskAnalysisServiceTest {

    @Test
    void testRiskAnalysisService() {
        StringBufferedWriterCreator creator = new StringBufferedWriterCreator();
        RiskAnalysisService exporter = new RiskAnalysisService(creator);

        DataManager.getInstance().addObserver(exporter);
        DataManager.getInstance().initRepo();

        CSVDataLoader loader = new CSVDataLoader("src/test/resources/testData.csv");
        WebDataSourceStatus status = loader.downloadFromWeb();

        assertEquals(WebDataSourceStatus.SUCCESS, status);

        DataManager.getInstance().flushRepo();
        DataManager.getInstance().removeObserver(exporter);

        final String expected = """
                Volatility: 25.25%
                Selected model: Anomalies (anomalies/all)
                Value: 4.17%
                """;

        assertEquals(expected, creator.getString());
    }
}