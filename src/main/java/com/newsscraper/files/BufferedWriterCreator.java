package com.newsscraper.files;

import java.io.BufferedWriter;

public interface BufferedWriterCreator {

    public BufferedWriter createWriter(String path);
}
