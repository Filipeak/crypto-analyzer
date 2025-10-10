package com.cryptoanalyzer.exporters;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;

public class CSVExporter extends FileExporter {

    public CSVExporter() {
        super();
    }

    public CSVExporter(BufferedWriterCreator creator) {
        super(creator);
    }


    @Override
    public String getName() {
        return "CSV Exporter";
    }


    @Override
    protected String getExtension() {
        return ".csv";
    }


    @Override
    public void onBegin() {
        super.onBegin();

        writeToFile("symbol,timestamp,open,close,high,low,volume\n");
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        String content = frame.symbol + "," + frame.timestamp + "," + frame.open + "," + frame.close + "," + frame.high + "," + frame.low + "," + frame.volume + "\n";

        writeToFile(content);
    }
}
