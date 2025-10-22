package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;

public class CSVExporterService extends FileExporterService {

    public CSVExporterService() {
        super();
    }

    public CSVExporterService(BufferedWriterCreator creator) {
        super(creator);
    }


    @Override
    public String getName() {
        return "CSV Exporter";
    }


    @Override
    protected String getCustomNameAndExtension() {
        return ".csv";
    }


    @Override
    public void onBegin() {
        super.onBegin();

        writeToFile("source,symbol,timestamp,open,close,high,low,volume\n");
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        String content = frame.source + "," + frame.symbol + "," + frame.timestamp + "," + frame.open + "," + frame.close + "," + frame.high + "," + frame.low + "," + frame.volume + "\n";

        writeToFile(content);
    }
}
