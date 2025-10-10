package com.newsscraper.sources;

import com.newsscraper.data.DataManager;
import com.newsscraper.data.WebDataFrame;
import com.newsscraper.data.WebDataReceiver;
import com.newsscraper.data.WebSourceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinanceDownloaderTest {

    @Test
    void downloadFromWebTest() {
        DownloadDummy dummy = new DownloadDummy();

        DataManager.getInstance().addObserver(dummy);
        DataManager.getInstance().initRepo();

        BinanceDownloader downloader = new BinanceDownloader();
        WebSourceStatus status = downloader.downloadFromWeb();

        assertEquals(WebSourceStatus.SUCCESS, status);
        assertEquals(24, dummy.count);

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