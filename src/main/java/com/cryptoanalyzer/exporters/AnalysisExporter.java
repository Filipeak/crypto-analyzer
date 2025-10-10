package com.cryptoanalyzer.exporters;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;

/**
 * TODO:
 *  - Standard deviation (=Volatility)
 *  - Average price
 *  - SMA
 *  - Price change in %
 *  - Momentum (close - open)
 *  - Single volatility (high - low)
 *  - Total volume
 *  - Average volume
 *  - Volume trend
 */
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
