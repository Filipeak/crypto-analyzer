package com.cryptoanalyzer.data;

public interface WebSource {

    public String getName();

    public WebSourceStatus downloadFromWeb();
}
