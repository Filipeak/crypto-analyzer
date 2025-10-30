package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;
import com.cryptoanalyzer.services.risk.HistoricalVaRStrategy;
import com.cryptoanalyzer.services.risk.LinearRegressionAnomaliesStrategy;
import com.cryptoanalyzer.services.risk.RiskComputationStrategy;

import java.util.ArrayList;

public class RiskAnalysisService extends FileExporterService {

    private final ArrayList<Float> pricesClose = new ArrayList<>();


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

        pricesClose.clear();
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        pricesClose.add(frame.close);
    }

    @Override
    public void onEnd() {
        if (!pricesClose.isEmpty()) {
            float mean = calculateMeanPrice();
            float var = calculatePriceVariance(mean);
            float volatility = calculateVolatility(var);
            float volatilityPercentage = volatility / pricesClose.getLast() * 100;
            RiskComputationStrategy strategy = null;

            if (volatilityPercentage <= 15.0f) {
                strategy = new HistoricalVaRStrategy();
            } else {
                strategy = new LinearRegressionAnomaliesStrategy();
            }

            String name = strategy.getName();
            float value = strategy.execute(DataManager.getInstance().getFrames());
            float valuePercentage = value * 100;

            writeToFile("Volatility: " + Math.round(volatilityPercentage * 100.0f) / 100.0f + "%\n");
            writeToFile("Selected model: " + name + "\n");
            writeToFile("Value: " + Math.round(valuePercentage * 100.0f) / 100.0f + "%\n");
        }

        super.onEnd();
    }


    private float calculateMeanPrice() {
        float mean = 0;

        for (float price : pricesClose) {
            mean += price;
        }

        mean /= pricesClose.size();

        return mean;
    }

    private float calculatePriceVariance(float mean) {
        float variance = 0;

        for (float price : pricesClose) {
            float diff = price - mean;
            variance += diff * diff;
        }

        variance /= pricesClose.size();

        return variance;
    }

    private float calculateVolatility(float var) {
        float std = (float) Math.sqrt(var);
        float t = 24.0f;

        return std * (float) Math.sqrt(t);
    }
}
