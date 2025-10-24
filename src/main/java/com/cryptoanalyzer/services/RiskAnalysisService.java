package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;
import com.cryptoanalyzer.services.risk.LinearRegressionAnomaliesStrategy;
import com.cryptoanalyzer.services.risk.RiskComputationStrategy;

public class RiskAnalysisService extends FileExporterService {

    public RiskAnalysisService() {
        super();
    }

    public RiskAnalysisService(BufferedWriterCreator creator) {
        super(creator);
    }


    @Override
    public String getName() {
        return "Risk Analysis";
    }


    @Override
    protected String getCustomNameAndExtension() {
        return "_RISK.txt";
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
        RiskComputationStrategy strategy = new LinearRegressionAnomaliesStrategy();
        float risk = strategy.execute(DataManager.getInstance().getFrames());

        writeToFile("Risk: " + risk * 100 + "%\n");

        super.onEnd();
    }
}
