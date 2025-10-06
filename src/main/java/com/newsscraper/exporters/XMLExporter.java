package com.newsscraper.exporters;

import com.newsscraper.data.WebDataFrame;

public class XMLExporter extends FileExporter {

    @Override
    public String getName() {
        return "XML Exporter";
    }


    @Override
    protected String getExtension() {
        return ".xml";
    }


    @Override
    public void onBegin() {
        super.onBegin();

        String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<frames>\n";

        writeToFile(content);
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        String content = "\t<frame>\n" +
                "\t\t<source>" + frame.source + "</source>\n" +
                "\t\t<title>" + formatTitle(frame.title) + "</title>\n" +
                "\t\t<url>" + frame.url + "</url>\n" +
                "\t\t<imgUrl>" + frame.imgUrl + "</imgUrl>\n" +
                "\t</frame>\n";

        writeToFile(content);
    }

    @Override
    public void onEnd() {
        writeToFile("</frames>\n");

        super.onEnd();
    }


    private String formatTitle(String title) {
        return title.replace("\"", "&quot;");
    }
}
