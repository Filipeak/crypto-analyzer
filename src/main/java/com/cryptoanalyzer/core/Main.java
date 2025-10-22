package com.cryptoanalyzer.core;

import com.cryptoanalyzer.data.*;
import com.cryptoanalyzer.services.*;
import com.cryptoanalyzer.logging.*;
import com.cryptoanalyzer.sources.*;
import com.cryptoanalyzer.ui.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Logger.getInstance().setLevel(LoggingLevel.DEBUG);
        Logger.getInstance().addSink(new ConsoleLoggingSink());
        Logger.getInstance().addSink(new FileLoggingSink());

        ArrayList<WebDataSource> sources = new ArrayList<>();
        sources.add(new BinanceDownloader());
        sources.add(new CoinbaseDownloader());

        ArrayList<DataService> services = new ArrayList<>();
        services.add(new CSVExporterService());
        services.add(new JSONExporterService());
        services.add(new XMLExporterService());
        services.add(new YAMLExporterService());
        services.add(new PriceAnalysisService());
        services.add(new VolumeAnalysisService());

        new MainPanel(sources, services);

        Logger.info("Init successful");
    }
}