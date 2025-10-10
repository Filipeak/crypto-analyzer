package com.cryptoanalyzer.logging;

public interface LoggingSink {

    public void debug(String message);

    public void info(String message);

    public void error(String message);

    public void close();
}
