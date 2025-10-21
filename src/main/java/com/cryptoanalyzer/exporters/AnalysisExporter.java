package com.cryptoanalyzer.exporters;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;

public class AnalysisExporter extends FileExporter {

    public AnalysisExporter() {
        super();
    }

    public AnalysisExporter(BufferedWriterCreator creator) {
        super(creator);
    }


    @Override
    public String getName() {
        return "Analysis Exporter";
    }


    @Override
    protected String getCustomNameAndExtension() {
        return "_analysis.txt";
    }


    @Override
    public void onBegin() {
        super.onBegin();
    }

    @Override
    public void onSetData(WebDataFrame frame) {
    }

    @Override
    public void onEnd() {
        super.onEnd();
    }
}
