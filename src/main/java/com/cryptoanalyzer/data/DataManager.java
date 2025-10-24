package com.cryptoanalyzer.data;

import com.cryptoanalyzer.logging.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class DataManager {
    private static DataManager instance;

    private final HashSet<WebDataReceiver> observers;
    private final ArrayList<WebDataFrame> frames;


    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }


    private DataManager() {
        observers = new HashSet<>();
        frames = new ArrayList<>();
    }


    public void addObserver(WebDataReceiver observer) {
        observers.add(observer);

        Logger.debug("Added observer to DataManager");
    }

    public void removeObserver(WebDataReceiver observer) {
        observers.remove(observer);

        Logger.debug("Removed observer from DataManager");
    }


    public void initRepo() {
        for (WebDataReceiver observer : observers) {
            observer.onBegin();
        }

        frames.clear();

        Logger.info("Prepared repository for data");
    }

    public void flushRepo() {
        for (WebDataReceiver observer : observers) {
            observer.onEnd();
        }

        frames.clear();

        Logger.info("Flushed repository");
    }

    public void pushWebDataFrame(WebDataFrame frame) {
        for (WebDataReceiver observer : observers) {
            observer.onSetData(frame);
        }

        frames.add(frame);

        Logger.debug("Pushed next data frame");
    }

    public List<WebDataFrame> getFrames() {
        return frames;
    }
}
