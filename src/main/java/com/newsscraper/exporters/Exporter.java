package com.newsscraper.exporters;

import com.newsscraper.data.WebDataReceiver;

public interface Exporter extends WebDataReceiver {

    public String getName();
}
