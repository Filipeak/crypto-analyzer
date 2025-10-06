package com.newsscraper.exporters;

import com.newsscraper.data.WebDataFrame;

public class JSONExporter extends FileExporter {

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

        writeToFile("[\n");
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        String content = "\t{\n" +
                "\t\t\"source\": \"" + frame.source + "\",\n" +
                "\t\t\"title\": \"" + formatTitle(frame.title) + "\",\n" +
                "\t\t\"url\": \"" + frame.url + "\",\n" +
                "\t\t\"imgUrl\": \"" + frame.imgUrl + "\",\n" +
                "\t},\n";

        writeToFile(content);
    }

    @Override
    public void onEnd() {
        writeToFile("]\n");

        super.onEnd();
    }


    private String formatTitle(String title) {
        return title.replace("\"", "\\\"");
    }
}
