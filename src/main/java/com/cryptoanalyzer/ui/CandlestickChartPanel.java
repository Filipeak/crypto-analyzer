package com.cryptoanalyzer.ui;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.data.WebDataReceiver;
import com.cryptoanalyzer.logging.Logger;
import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.OHLCChartBuilder;
import org.knowm.xchart.SwingWrapper;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

public class CandlestickChartPanel implements WebDataReceiver {

    private Map<LocalDate, CandleData> candles = new HashMap<>();


    public CandlestickChartPanel() {
        DataManager.getInstance().addObserver(this);

        Logger.info("Initialized candlestick chart");
    }


    @Override
    public void onBegin() {
        candles.clear();
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        LocalDate localDate = LocalDate.ofInstant(new Date(((long) frame.timestamp) * 1000L).toInstant(), ZoneOffset.UTC);
        CandleData data = candles.get(localDate);

        if (data != null) {
            data.high = Math.max(data.high, frame.high);
            data.low = Math.min(data.low, frame.low);
            data.close = frame.close;
        } else {
            data = new CandleData();
            data.open = frame.open;
            data.high = frame.high;
            data.low = frame.low;
            data.close = frame.close;

            candles.put(localDate, data);
        }
    }

    @Override
    public void onEnd() {
        if (candles.isEmpty()) {
            return;
        }

        Logger.info("Showing candlestick chart...");

        List<Date> dates = new ArrayList<>();
        List<Double> open = new ArrayList<>();
        List<Double> high = new ArrayList<>();
        List<Double> low = new ArrayList<>();
        List<Double> close = new ArrayList<>();

        for (LocalDate localDate : candles.keySet()) {
            CandleData data = candles.get(localDate);

            dates.add(Date.from(localDate.atStartOfDay(ZoneOffset.UTC).toInstant()));
            open.add((double) data.open);
            high.add((double) data.high);
            low.add((double) data.low);
            close.add((double) data.close);
        }

        OHLCChart chart = new OHLCChartBuilder()
                .width(800)
                .height(600)
                .title("BTC USD / D1")
                .xAxisTitle("Date")
                .yAxisTitle("Price")
                .build();

        chart.addSeries("Price", dates, open, high, low, close);

        new Thread(() -> {
            JFrame frame = new SwingWrapper<>(chart).displayChart();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }).start();
    }


    private class CandleData {
        float open;
        float high;
        float low;
        float close;
    }
}
