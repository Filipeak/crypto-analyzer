package com.cryptoanalyzer.exporters;

import com.cryptoanalyzer.data.WebDataReceiver;

public interface Exporter extends WebDataReceiver {

    public String getName();
}
