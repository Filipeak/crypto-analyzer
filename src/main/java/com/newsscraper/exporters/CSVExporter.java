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

        writeToFile("source,title,url,imgUrl\n");
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        String content = String.format("\"%s\",\"%s\",\"%s\",\"%s\"\n",
                formatField(frame.source),
                formatField(frame.title),
                formatField(frame.url),
                formatField(frame.imgUrl));

        writeToFile(content);
    }


    private String formatField(String field) {
        return field.replace("\"", "\\\"");
    }
}
