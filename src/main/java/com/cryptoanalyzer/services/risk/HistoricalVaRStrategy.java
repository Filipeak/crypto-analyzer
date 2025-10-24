package com.cryptoanalyzer.services.risk;

import com.cryptoanalyzer.data.WebDataFrame;

import java.util.ArrayList;
import java.util.List;

public class HistoricalVaRStrategy implements RiskComputationStrategy {

    private static final float CONFIDENCE_LEVEL = 0.95f;

    @Override
    public float execute(List<WebDataFrame> data) {
        ArrayList<Float> dailyReturns = new ArrayList<>();

        for (int i = 0; i < data.size(); i += 24) {
            float ret = (data.get(i + 23).close - data.get(i).open) / data.get(i).open;

            dailyReturns.add(ret);
        }

        dailyReturns.sort(Float::compareTo);

        int n = Math.max((int)Math.ceil(dailyReturns.size() * (1 - CONFIDENCE_LEVEL)), 1);

        return dailyReturns.get(n - 1);
    }
}
