package com.cryptoanalyzer.core;

import com.cryptoanalyzer.data.*;
import com.cryptoanalyzer.exporters.*;
import com.cryptoanalyzer.logging.*;
import com.cryptoanalyzer.sources.*;
import com.cryptoanalyzer.ui.*;

import java.util.ArrayList;

// TODO: Change 24h -> 30d
// TODO: Analysis manager (new exporter) + Tests
// TODO: UML diagrams

public class Main {
    public static void main(String[] args) {
        Logger.setLevel(LoggingLevel.DEBUG);
        Logger.getInstance().addSink(new ConsoleLoggingSink());
        Logger.getInstance().addSink(new FileLoggingSink());

        ArrayList<WebSource> sources = new ArrayList<>();
        sources.add(new BinanceDownloader());
        sources.add(new CoinbaseDownloader());

        ArrayList<Exporter> exporters = new ArrayList<>();
        exporters.add(new CSVExporter());
        exporters.add(new JSONExporter());
        exporters.add(new XMLExporter());
        exporters.add(new YAMLExporter());
        exporters.add(new AnalysisExporter());

        new MainPanel(sources, exporters);

        Logger.info("Init successful");
    }
}