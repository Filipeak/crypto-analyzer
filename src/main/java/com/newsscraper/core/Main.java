package com.newsscraper.core;

import com.newsscraper.data.*;
import com.newsscraper.exporters.*;
import com.newsscraper.logging.*;
import com.newsscraper.sources.*;
import com.newsscraper.ui.*;

import java.util.ArrayList;

// TODO: File Logger Sink, Unit Tests, News Impl

public class Main {
    public static void main(String[] args) {
        Logger.setLevel(LoggingLevel.DEBUG);
        Logger.getInstance().addSink(new ConsoleLoggingSink());
        Logger.getInstance().addSink(new FileLoggingSink());

        ArrayList<WebSource> sources = new ArrayList<>();
        sources.add(new GazetaPolskaDownloader());
        sources.add(new WirtualnaPolskaDownloader());

        ArrayList<Exporter> exporters = new ArrayList<>();
        exporters.add(new CSVExporter());
        exporters.add(new JSONExporter());
        exporters.add(new XMLExporter());
        exporters.add(new YAMLExporter());

        new MainPanel(sources, exporters);

        Logger.info("Init successful");
    }
}