package com.newsscraper.files;

import com.newsscraper.logging.Logger;

import java.io.*;

public class FileBufferedWriterCreator implements BufferedWriterCreator {

    @Override
    public BufferedWriter createWriter(String path) {
        try {
            ensureDirectoryExists(path);

            File file = new File(path);

            if (file.createNewFile()) {
                Logger.info("File created: " + path);

                return new BufferedWriter(new FileWriter(file));
            } else {
                Logger.error("File already exists: " + path);

                return null;
            }
        } catch (IOException e) {
            Logger.error(e.getMessage());

            return null;
        }
    }


    private void ensureDirectoryExists(String fileName) {
        File dir = new File(fileName).getParentFile();

        if (!dir.exists()) {
            if (dir.mkdir()) {
                Logger.info("Base directory for files created");
            } else {
                Logger.error("Unknown error while creating directory");
            }
        }
    }
}
