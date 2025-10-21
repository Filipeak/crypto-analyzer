package com.cryptoanalyzer.exporters;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;

import java.util.ArrayList;

public class AnalysisExporter extends FileExporter {

    private int count;
    private float totalVolume;
    private final ArrayList<Float> pricesOpen = new ArrayList<>();
    private final ArrayList<Float> pricesClose = new ArrayList<>();


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
        count = 0;
        totalVolume = 0;
        pricesOpen.clear();
        pricesClose.clear();

        super.onBegin();
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        count++;
        totalVolume += frame.volume;
        pricesOpen.add(frame.open);
        pricesClose.add(frame.close);
    }

    @Override
    public void onEnd() {
        writeToFile("====== VOLUME =======\n");
        writeToFile("- Total Volume: " + totalVolume + "\n");
        writeToFile("- Average Volume: " + totalVolume / count + "\n");

        writeToFile("\n");

        writeToFile("====== PRICE =======\n");

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
