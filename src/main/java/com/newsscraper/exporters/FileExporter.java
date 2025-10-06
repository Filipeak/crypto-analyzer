package com.newsscraper.exporters;

import com.newsscraper.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class FileExporter implements Exporter {

    private static final String BASE_DIR = "reports/";

    private File file;
    private BufferedWriter writer;


    protected abstract String getExtension();

    private void ensureDirectoryExists() {
        File dir = new File(BASE_DIR);

        if (!dir.exists()) {
            if (dir.mkdir()) {
                Logger.info("Base directory for files created");
            } else {
                Logger.error("Unknown error while creating directory");
            }
        }
    }

    private String getFileName() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");

        return BASE_DIR + "NewsScraperReport_" + formatter.format(new Date()) + getExtension();
    }

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
        try {
            ensureDirectoryExists();

            String fileName = getFileName();
            file = new File(fileName);

            if (file.createNewFile()) {
                Logger.info("File created: " + fileName);

                writer = new BufferedWriter(new FileWriter(file));
            } else {
                Logger.error("File already exists: " + fileName);
            }
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }

    @Override
    public void onEnd() {
        try {
            if (writer != null) {
                writer.close();
                writer = null;
            }
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }

        if (file != null) {
            Logger.info("File \"" + file.getName() + "\" closed");

            file = null;
        }
    }
}