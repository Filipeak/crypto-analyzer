package com.newsscraper.data;

public class WebDataFrame {
    public String source;
    public String title;
    public String url;
    public String imgUrl;

    public WebDataFrame(String source, String title, String url, String imgUrl) {
        this.source = source;
        this.title = title;
        this.url = url;
        this.imgUrl = imgUrl;
    }
}