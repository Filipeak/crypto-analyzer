package com.cryptoanalyzer.sources;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.data.WebDataReceiver;
import com.cryptoanalyzer.data.WebDataSourceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVDataLoaderTest {

    @Test
    void testLoadCSVData() {
        DownloadDummy dummy = new DownloadDummy();

        DataManager.getInstance().addObserver(dummy);
        DataManager.getInstance().initRepo();

        CSVDataLoader loader = new CSVDataLoader("src/test/resources/testData.csv");
        WebDataSourceStatus status = loader.downloadFromWeb();

        assertEquals(WebDataSourceStatus.SUCCESS, status);
        assertEquals(720, dummy.count);

        DataManager.getInstance().flushRepo();
    }


    private class DownloadDummy implements WebDataReceiver {

        public int count = 0;

        @Override
        public void onBegin() {
        }

        @Override
        public void onSetData(WebDataFrame frame) {
            count++;
        }

        @Override
        public void onEnd() {
        }
    }
}