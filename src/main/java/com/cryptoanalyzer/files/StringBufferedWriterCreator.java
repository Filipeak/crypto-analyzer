package com.cryptoanalyzer.files;

import java.io.BufferedWriter;
import java.io.StringWriter;

public class StringBufferedWriterCreator implements BufferedWriterCreator {

    private StringWriter stringWriter;

    @Override
    public BufferedWriter createWriter(String path) {
        stringWriter = new StringWriter();

        return new BufferedWriter(stringWriter);
    }

    public String getString() {
        return stringWriter.toString();
    }
}
