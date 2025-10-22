package com.cryptoanalyzer.sources;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.data.WebDataSource;
import com.cryptoanalyzer.data.WebDataSourceStatus;
import com.cryptoanalyzer.logging.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class CSVDataLoader implements WebDataSource {

    private String fileName;

    public CSVDataLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public WebDataSourceStatus downloadFromWeb() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            boolean headerSkipped = false;

            while (line != null) {
                if (headerSkipped) {
                    List<String> values = Arrays.asList(line.split(","));

                    DataManager.getInstance().pushWebDataFrame(new WebDataFrame(
                            values.get(0),
                            values.get(1),
                            Integer.parseInt(values.get(2)),
                            Float.parseFloat(values.get(3)),
                            Float.parseFloat(values.get(4)),
                            Float.parseFloat(values.get(5)),
                            Float.parseFloat(values.get(6)),
                            Float.parseFloat(values.get(7))
                    ));
                } else {
                    headerSkipped = true;
                }

                line = br.readLine();
            }

            return WebDataSourceStatus.SUCCESS;
        } catch (Exception e) {
            Logger.error(e.getMessage());

            return WebDataSourceStatus.FAILURE_NO_DATA;
        }
    }
}
