package com.cryptoanalyzer.files;

import java.io.BufferedWriter;

public interface BufferedWriterCreator {

    public BufferedWriter createWriter(String path);
}
