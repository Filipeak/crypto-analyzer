package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;

import java.util.ArrayList;

public class AnomaliesDetectionService extends FileExporterService {

    private final ArrayList<Float> pricesDiffs = new ArrayList<>();
    private final ArrayList<Float> volumes = new ArrayList<>();


    public AnomaliesDetectionService() {
        super();
    }

    public AnomaliesDetectionService(BufferedWriterCreator creator) {
        super(creator);
    }


    @Override
    public String getName() {
        return "Anomalies Detection";
    }


    @Override
    protected String getCustomNameAndExtension() {
        return "_anomalies.txt";
    }


    @Override
    public void onBegin() {
        pricesDiffs.clear();
        volumes.clear();

        super.onBegin();
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        pricesDiffs.add(frame.high - frame.low);
        volumes.add(frame.volume);
    }

    @Override
    public void onEnd() {
        super.onEnd();
    }
}
