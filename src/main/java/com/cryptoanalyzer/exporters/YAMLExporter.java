package com.cryptoanalyzer.exporters;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;

public class YAMLExporter extends FileExporter {

    public YAMLExporter() {
        super();
    }

    public YAMLExporter(BufferedWriterCreator creator) {
        super(creator);
    }


    @Override
    public String getName() {
        return "YAML Exporter";
    }


    @Override
    protected String getCustomNameAndExtension() {
        return ".yml";
    }


    @Override
    public void onSetData(WebDataFrame frame) {
        String content = "-\n" +
                "  symbol: " + frame.symbol + "\n" +
                "  timestamp: " + frame.timestamp + "\n" +
                "  open: " + frame.open + "\n" +
                "  close: " + frame.close + "\n" +
                "  high: " + frame.high + "\n" +
                "  low: " + frame.low + "\n" +
                "  volume: " + frame.volume + "\n";

        writeToFile(content);
    }
}
