package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;

import java.util.ArrayList;

public class PriceAnalysisService extends FileExporterService {

    private int count;
    private final ArrayList<Float> pricesOpen = new ArrayList<>();
    private final ArrayList<Float> pricesClose = new ArrayList<>();


    public PriceAnalysisService() {
        super();
    }

    public PriceAnalysisService(BufferedWriterCreator creator) {
        super(creator);
    }


    private float calculateMeanPrice() {
        float mean = 0;

        for (float price : pricesClose) {
            mean += price;
        }

        mean /= count;

        return mean;
    }

    private float calculatePriceVariance(float mean) {
        float variance = 0;

        for (float price : pricesClose) {
            float diff = price - mean;
            variance += diff * diff;
        }

        variance /= count;

        return variance;
    }

    private float calculateVolatility(float var) {
        float std = (float) Math.sqrt(var);
        float t = (float) count / 24;
        float volatility = std * (float) Math.sqrt(t);

        return volatility;
    }


    @Override
    public String getName() {
        return "Price Analysis";
    }


    @Override
    protected String getCustomNameAndExtension() {
        return "_priceAnalysis.txt";
    }


    @Override
    public void onBegin() {
        count = 0;
        pricesOpen.clear();
        pricesClose.clear();

        super.onBegin();
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        count++;
        pricesOpen.add(frame.open);
        pricesClose.add(frame.close);
    }

    @Override
    public void onEnd() {
        float totalReturn = (pricesClose.getLast() - pricesOpen.getFirst()) / pricesOpen.getFirst();
        float averageReturn = 0;
        float maxReturn = 0;

        for (int i = 0; i < pricesClose.size(); i++) {
            float diff = (pricesClose.get(i) - pricesOpen.get(i)) / pricesOpen.get(i);

            if (diff > maxReturn) {
                maxReturn = diff;
            }

            averageReturn += diff;
        }

        averageReturn /= count;

        writeToFile("- Total Change: " + totalReturn * 100 + "%\n");
        writeToFile("- Average Change: " + averageReturn * 100 + "%\n");
        writeToFile("- Max Change: " + maxReturn * 100 + "%\n");

        float mean = calculateMeanPrice();
        float var = calculatePriceVariance(mean);
        float volatility = calculateVolatility(var);
        float volatilityPercentage = volatility / pricesClose.getLast() * 100;

        writeToFile("- Monthly Volatility: " + volatility + " = " + volatilityPercentage + "%\n");

        super.onEnd();
    }
}
