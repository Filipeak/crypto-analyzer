package com.newsscraper.logging;

import java.util.HashSet;

public final class Logger {

    private static Logger instance;

    private final HashSet<LoggingSink> sinks;
    private LoggingLevel currentLevel;


    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }


    public Logger() {
        sinks = new HashSet<>();
        currentLevel = LoggingLevel.DEBUG;
    }


    public void addSink(LoggingSink sink) {
        sinks.add(sink);
    }

    public void removeSink(LoggingSink sink) {
        sinks.remove(sink);
    }


    public static void setLevel(LoggingLevel level) {
        getInstance().currentLevel = level;
    }

    public static void debug(String message) {
        if (getInstance().currentLevel.ordinal() > LoggingLevel.DEBUG.ordinal()) {
            return;
        }

        for (LoggingSink sink : getInstance().sinks) {
            sink.debug(message);
        }
    }

    public static void info(String message) {
        if (getInstance().currentLevel.ordinal() > LoggingLevel.INFO.ordinal()) {
            return;
        }

        for (LoggingSink sink : getInstance().sinks) {
            sink.info(message);
        }
    }

    public static void error(String message) {
        if (getInstance().currentLevel.ordinal() > LoggingLevel.ERROR.ordinal()) {
            return;
        }

        for (LoggingSink sink : getInstance().sinks) {
            sink.error(message);
        }
    }

    public static void shutdown() {
        for (LoggingSink sink : getInstance().sinks) {
            sink.close();
        }
    }
}
