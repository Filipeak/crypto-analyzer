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
        float changePercentage = (pricesClose.getLast() - pricesOpen.getFirst()) / pricesOpen.getFirst() * 100;

        writeToFile("- Change: " + changePercentage + "%\n");

        float mean = 0;

        for (float price : pricesClose) {
            mean += price;
        }

        mean /= count;

        float variance = 0;

        for (float price : pricesClose) {
            float diff = price - mean;
            variance += diff * diff;
        }

        variance /= count;

        float std = (float) Math.sqrt(variance);
        float t = (float) count / 24;
        float volatility = std * (float) Math.sqrt(t);
        float volatilityPercentage = volatility / pricesClose.getLast() * 100;

        writeToFile("- Monthly Volatility: " + volatility + " = " + volatilityPercentage + "%\n");

        super.onEnd();
    }
}
