package com.newsscraper.exporters;

import com.newsscraper.data.WebDataFrame;

public class YAMLExporter extends FileExporter {

    @Override
    public String getName() {
        return "YAML Exporter";
    }


    @Override
    protected String getExtension() {
        return ".yml";
    }


    @Override
    public void onSetData(WebDataFrame frame) {
        String content = "-\n" +
                "  source: \"" + frame.source + "\"\n" +
                "  title: \"" + formatTitle(frame.title) + "\"\n" +
                "  url: \"" + frame.url + "\"\n" +
                "  imgUrl: \"" + frame.imgUrl + "\"\n";

        writeToFile(content);
    }


    private String formatTitle(String title) {
        return title.replace("\"", "\\\"");
    }
}
