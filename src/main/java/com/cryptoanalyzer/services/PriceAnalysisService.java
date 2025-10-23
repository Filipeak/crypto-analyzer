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

        return std * (float) Math.sqrt(t);
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
        if (count == 0 || count % 24 != 0 || count != pricesOpen.size() || count != pricesClose.size()) {
            writeToFile("INVALID DATA");

            super.onEnd();

            return;
        }

        float totalReturn = (pricesClose.getLast() - pricesOpen.getFirst()) / pricesOpen.getFirst();
        float averageReturn = 0;
        float maxReturn = 0;

        for (int i = 0; i < pricesClose.size(); i += 24) {
            float diff = (pricesClose.get(i + 23) - pricesOpen.get(i)) / pricesOpen.get(i);

            if (diff > maxReturn) {
                maxReturn = diff;
            }

            averageReturn += diff;
        }

        averageReturn /= (float) count / 24;

        writeToFile("- Total Change: " + totalReturn * 100 + "%\n");
        writeToFile("- Average Daily Change: " + averageReturn * 100 + "%\n");
        writeToFile("- Max Daily Change: " + maxReturn * 100 + "%\n");

        float mean = calculateMeanPrice();
        float var = calculatePriceVariance(mean);
        float volatility = calculateVolatility(var);
        float volatilityPercentage = volatility / pricesClose.getLast() * 100;

        writeToFile("- Monthly Volatility: " + volatility + " = " + volatilityPercentage + "%\n");

        super.onEnd();
    }
}
