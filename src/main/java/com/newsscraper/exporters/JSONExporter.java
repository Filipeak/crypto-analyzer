package com.newsscraper.exporters;

import com.newsscraper.data.WebDataFrame;
import com.newsscraper.files.BufferedWriterCreator;

public class JSONExporter extends FileExporter {

    private boolean isFirstFrame;


    public JSONExporter() {
        super();
    }

    public JSONExporter(BufferedWriterCreator creator) {
        super(creator);
    }


    @Override
    public String getName() {
        return "JSON Exporter";
    }


    @Override
    protected String getExtension() {
        return ".json";
    }


    @Override
    public void onBegin() {
        super.onBegin();

        isFirstFrame = true;

        writeToFile("[\n");
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        String forwardLine = isFirstFrame ? "" : ",\n";
        String content = "\t{\n" +
                "\t\t\"symbol\": \"" + frame.symbol + "\",\n" +
                "\t\t\"timestamp\": " + frame.timestamp + ",\n" +
                "\t\t\"open\": " + frame.open + ",\n" +
                "\t\t\"close\": " + frame.close + ",\n" +
                "\t\t\"high\": " + frame.high + ",\n" +
                "\t\t\"low\": " + frame.low + ",\n" +
                "\t\t\"volume\": " + frame.volume + "\n" +
                "\t}";

        isFirstFrame = false;

        writeToFile(forwardLine + content);
    }

    @Override
    public void onEnd() {
        writeToFile("\n]\n");

        super.onEnd();
    }
}
