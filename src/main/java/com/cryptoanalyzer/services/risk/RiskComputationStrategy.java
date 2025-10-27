package com.cryptoanalyzer.services.risk;

import com.cryptoanalyzer.data.WebDataFrame;

import java.util.List;

public interface RiskComputationStrategy {

    public String getName();

    public float execute(List<WebDataFrame> data);
}
