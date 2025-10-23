package com.cryptoanalyzer.sources;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.data.WebDataReceiver;
import com.cryptoanalyzer.data.WebDataSourceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinbaseDownloaderTest {

    @Test
    void testDownloadFromCoinbase() {
        DownloadDummy dummy = new DownloadDummy();

        DataManager.getInstance().addObserver(dummy);
        DataManager.getInstance().initRepo();

        CoinbaseDownloader downloader = new CoinbaseDownloader();
        WebDataSourceStatus status = downloader.downloadFromWeb();

        assertEquals(WebDataSourceStatus.SUCCESS, status);
        assertEquals(720, dummy.count);

        DataManager.getInstance().flushRepo();
        DataManager.getInstance().removeObserver(dummy);
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