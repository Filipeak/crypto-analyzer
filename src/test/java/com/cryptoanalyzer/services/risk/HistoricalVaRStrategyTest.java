package com.cryptoanalyzer.services.risk;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataSourceStatus;
import com.cryptoanalyzer.sources.CSVDataLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoricalVaRStrategyTest {

    @Test
    void testExecute() {
        DataManager.getInstance().initRepo();

        CSVDataLoader loader = new CSVDataLoader("src/test/resources/testData.csv");
        WebDataSourceStatus status = loader.downloadFromWeb();

        assertEquals(WebDataSourceStatus.SUCCESS, status);

        HistoricalVaRStrategy strategy = new HistoricalVaRStrategy();
        float risk = strategy.execute(DataManager.getInstance().getFrames());

        DataManager.getInstance().flushRepo();

        assertEquals(-0.03822051f, risk);
    }
}