package com.newsscraper.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLoggingSink implements LoggingSink {

    private static final String BASE_DIR = "logs/";

    private BufferedWriter writer;


    private void ensureDirectoryExists() {
        File dir = new File(BASE_DIR);

        if (!dir.exists()) {
            if (!dir.mkdir()) {
                System.err.println("Unknown error while creating directory");
            }
        }
    }

    private String getFileName() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");

        return BASE_DIR + "logs_" + formatter.format(new Date()) + ".txt";
    }

    private void writeToFile(String content) {
        try {
            if (writer != null) {
                writer.append(content);
                writer.newLine();
                writer.flush();
            } else {
                System.err.println("Writer is null");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public FileLoggingSink() {
        try {
            ensureDirectoryExists();

            String fileName = getFileName();
            File file = new File(fileName);

            if (file.createNewFile()) {
                writer = new BufferedWriter(new FileWriter(file));
            } else {
                System.err.println("File already exists: " + fileName);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void debug(String message) {
        writeToFile("[DEBUG] " + message);
    }

    @Override
    public void info(String message) {
        writeToFile("[INFO] " + message);
    }

    @Override
    public void error(String message) {
        writeToFile("[ERROR] " + message);
    }

    @Override
    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
