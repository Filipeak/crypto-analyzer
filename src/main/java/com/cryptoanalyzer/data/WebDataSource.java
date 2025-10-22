package com.cryptoanalyzer.data;

public interface WebDataSource {

    public String getName();

    public WebDataSourceStatus downloadFromWeb();
}
