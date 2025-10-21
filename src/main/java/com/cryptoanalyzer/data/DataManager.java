package com.cryptoanalyzer.data;

import com.cryptoanalyzer.logging.Logger;

import java.util.HashSet;

public final class DataManager {
    private static DataManager instance;

    private final HashSet<WebDataReceiver> observers;


    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }


    private DataManager() {
        observers = new HashSet<>();
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

        Logger.info("Prepared repository for data");
    }

    public void flushRepo() {
        for (WebDataReceiver observer : observers) {
            observer.onEnd();
        }

        Logger.info("Flushed repository");
    }

    public void pushWebDataFrame(WebDataFrame frame) {
        for (WebDataReceiver observer : observers) {
            observer.onSetData(frame);
        }

        Logger.debug("Pushed next data frame");
    }
}
