package com.cryptoanalyzer.services;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.files.BufferedWriterCreator;

public class VolumeAnalysisService extends FileExporterService {

    private int count;
    private float totalVolume;


    public VolumeAnalysisService() {
        super();
    }

    public VolumeAnalysisService(BufferedWriterCreator creator) {
        super(creator);
    }


    @Override
    public String getName() {
        return "Volume Analysis";
    }


    @Override
    protected String getCustomNameAndExtension() {
        return "_volumeAnalysis.txt";
    }


    @Override
    public void onBegin() {
        count = 0;
        totalVolume = 0;

        super.onBegin();
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        count++;
        totalVolume += frame.volume;
    }

    @Override
    public void onEnd() {
        writeToFile("- Total Volume: " + totalVolume + "\n");
        writeToFile("- Average Volume: " + totalVolume / count + "\n");

        super.onEnd();
    }
}
