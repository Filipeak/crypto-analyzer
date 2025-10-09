package com.newsscraper.sources;

import com.newsscraper.data.WebSourceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinbaseDownloaderTest {

    @Test
    void downloadFromWeb() {
        CoinbaseDownloader downloader = new CoinbaseDownloader();
        WebSourceStatus status = downloader.downloadFromWeb();

        assertEquals(WebSourceStatus.SUCCESS, status);
    }
}