package com.cryptoanalyzer.services.risk;

import com.cryptoanalyzer.data.WebDataFrame;

import java.util.List;

public interface RiskComputationStrategy {

    public float execute(List<WebDataFrame> data);
}
