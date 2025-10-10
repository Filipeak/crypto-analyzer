package com.cryptoanalyzer.exporters;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;

public class XMLExporter extends FileExporter {

    public XMLExporter() {
        super();
    }

    public XMLExporter(BufferedWriterCreator creator) {
        super(creator);
    }


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

        String content = """
                <?xml version="1.0" encoding="utf-8"?>
                <frames>
                """;

        writeToFile(content);
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        String content = "\t<frame>\n" +
                "\t\t<symbol>" + frame.symbol + "</symbol>\n" +
                "\t\t<timestamp>" + frame.timestamp + "</timestamp>\n" +
                "\t\t<open>" + frame.open + "</open>\n" +
                "\t\t<close>" + frame.close + "</close>\n" +
                "\t\t<high>" + frame.high + "</high>\n" +
                "\t\t<low>" + frame.low + "</low>\n" +
                "\t\t<volume>" + frame.volume + "</volume>\n" +
                "\t</frame>\n";

        writeToFile(content);
    }

    @Override
    public void onEnd() {
        writeToFile("</frames>\n");

        super.onEnd();
    }
}
