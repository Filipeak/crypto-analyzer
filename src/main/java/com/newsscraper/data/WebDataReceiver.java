package com.newsscraper.data;

public interface WebDataReceiver {

    public void onBegin();

    public void onSetData(WebDataFrame frame);

    public void onEnd();
}
