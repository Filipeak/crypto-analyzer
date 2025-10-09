package com.newsscraper.data;

public class WebDataFrame {
    public String symbol;
    public long timestamp;
    public float open;
    public float close;
    public float high;
    public float low;
    public float volume;

    public WebDataFrame(String symbol, long timestamp, float open, float close, float high, float low, float volume) {
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }
}