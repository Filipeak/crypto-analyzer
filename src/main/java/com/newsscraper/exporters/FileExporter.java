package com.newsscraper.exporters;

import com.newsscraper.files.FileBufferedWriterCreator;
import com.newsscraper.files.BufferedWriterCreator;
import com.newsscraper.logging.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class FileExporter implements Exporter {

    private static final String BASE_DIR = "reports/";

    private final BufferedWriterCreator creator;
    private BufferedWriter writer;


    public FileExporter() {
        this.creator = new FileBufferedWriterCreator();
    }

    public FileExporter(BufferedWriterCreator creator) {
        this.creator = creator;
    }


    protected abstract String getExtension();

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
        String fileName = BASE_DIR + "CryptoData_" + formatter.format(new Date()) + getExtension();

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