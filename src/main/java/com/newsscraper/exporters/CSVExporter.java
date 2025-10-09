package com.newsscraper.exporters;

import com.newsscraper.data.WebDataFrame;

public class CSVExporter extends FileExporter {

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
        String content = String.format("\"%s\",\"%d\",\"%f\",\"%f\",\"%f\",\"%f\",\"%f\"\n",
                frame.symbol,
                frame.timestamp,
                frame.open,
                frame.close,
                frame.high,
                frame.low,
                frame.volume);

        writeToFile(content);
    }
}
