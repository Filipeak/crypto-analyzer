package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.data.WebDataSourceStatus;
import com.cryptoanalyzer.files.StringBufferedWriterCreator;
import com.cryptoanalyzer.sources.CSVDataLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VolumeAnalysisServiceTest {

    @Test
    void testOneFrameSave() {
        StringBufferedWriterCreator creator = new StringBufferedWriterCreator();
        VolumeAnalysisService exporter = new VolumeAnalysisService(creator);

        DataManager.getInstance().addObserver(exporter);
        DataManager.getInstance().initRepo();

        CSVDataLoader loader = new CSVDataLoader("src/test/resources/testData.csv");
        WebDataSourceStatus status = loader.downloadFromWeb();

        assertEquals(WebDataSourceStatus.SUCCESS, status);

        DataManager.getInstance().flushRepo();
        DataManager.getInstance().removeObserver(exporter);

        final String expected = """
                - Total Volume: 662936.75
                - Average Daily Volume: 22097.893
                - Average Hourly Volume: 920.7455
                """;

        assertEquals(expected, creator.getString());
    }
}