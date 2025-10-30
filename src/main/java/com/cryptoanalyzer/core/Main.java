package com.cryptoanalyzer.core;

import com.cryptoanalyzer.data.*;
import com.cryptoanalyzer.services.*;
import com.cryptoanalyzer.logging.*;
import com.cryptoanalyzer.sources.*;
import com.cryptoanalyzer.ui.*;

public class Main {
    public static void main(String[] args) {
        Logger.getInstance().setLevel(LoggingLevel.DEBUG);
        Logger.getInstance().addSink(new ConsoleLoggingSink());
        Logger.getInstance().addSink(new FileLoggingSink());

        WebDataSource[] sources = new WebDataSource[]{
                new BinanceDownloader(),
                new CoinbaseDownloader(),
        };

        DataService[] services = new DataService[]{
                new CSVExporterService(),
                new JSONExporterService(),
                new XMLExporterService(),
                new YAMLExporterService(),
                new RiskAnalysisService(),
        };

        new MainPanel(sources, services);

        Logger.info("Init successful");
    }
}