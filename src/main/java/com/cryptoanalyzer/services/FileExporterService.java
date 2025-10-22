package com.cryptoanalyzer.services;

import com.cryptoanalyzer.files.FileBufferedWriterCreator;
import com.cryptoanalyzer.files.BufferedWriterCreator;
import com.cryptoanalyzer.logging.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class FileExporterService implements DataService {

    private static final String BASE_DIR = "reports/";

    private final BufferedWriterCreator creator;
    private BufferedWriter writer;


    public FileExporterService() {
        this.creator = new FileBufferedWriterCreator();
    }

    public FileExporterService(BufferedWriterCreator creator) {
        this.creator = creator;
    }


    protected abstract String getCustomNameAndExtension();

    protected void writeToFile(String content) {
        try {
            if (writer != null) {
                writer.append(content);
            } else {
                Logger.error("Writer is null");
            }
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }


    @Override
    public void onBegin() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
        String fileName = BASE_DIR + "CryptoData_" + formatter.format(new Date()) + getCustomNameAndExtension();

        writer = creator.createWriter(fileName);
    }

    @Override
    public void onEnd() {
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
                writer = null;

                Logger.info("Stream writer closed");
            }
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }
}