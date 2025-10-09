package com.newsscraper.sources;

import com.newsscraper.data.WebSourceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinanceDownloaderTest {

    @Test
    void downloadFromWebTest() {
        BinanceDownloader downloader = new BinanceDownloader();
        WebSourceStatus status = downloader.downloadFromWeb();

        assertEquals(WebSourceStatus.SUCCESS, status);
    }
}