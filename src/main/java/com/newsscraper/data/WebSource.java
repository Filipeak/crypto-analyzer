package com.newsscraper.data;

public interface WebSource {

    public String getName();

    public WebSourceStatus downloadFromWeb();
}
