package com.cryptoanalyzer.sources;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.data.WebDataReceiver;

public class DownloadDummy implements WebDataReceiver {

    public int count = 0;
    public boolean isNull = false;
    public boolean invalidTimestamp = false;

    private int lastTimestamp = -1;

    @Override
    public void onBegin() {
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        count++;

        if (frame == null) {
            isNull = true;
        }

        if (lastTimestamp >= 0) {
            int diff = frame.timestamp - lastTimestamp;

            if (diff != 3600) {
                invalidTimestamp = true;
            }
        }

        lastTimestamp = frame.timestamp;
    }

    @Override
    public void onEnd() {
    }
}