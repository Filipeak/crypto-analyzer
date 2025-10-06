package com.newsscraper.exporters;

import com.newsscraper.data.WebDataFrame;

public class JSONExporter extends FileExporter {

    private boolean isFirstFrame;

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
                "\t\t\"source\": \"" + frame.source + "\",\n" +
                "\t\t\"title\": \"" + formatTitle(frame.title) + "\",\n" +
                "\t\t\"url\": \"" + frame.url + "\",\n" +
                "\t\t\"imgUrl\": \"" + frame.imgUrl + "\"\n" +
                "\t}";

        isFirstFrame = false;

        writeToFile(forwardLine + content);
    }

    @Override
    public void onEnd() {
        writeToFile("\n]\n");

        super.onEnd();
    }


    private String formatTitle(String title) {
        return title.replace("\"", "\\\"");
    }
}
