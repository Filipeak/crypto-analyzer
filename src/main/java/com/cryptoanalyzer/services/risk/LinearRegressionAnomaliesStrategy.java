package com.cryptoanalyzer.services.risk;

import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class LinearRegressionAnomaliesStrategy implements RiskComputationStrategy {

    private static final double EPOCH_COUNT = 10000;
    private static final float LEARNING_RATE = 0.0000001f;
    private static final float CONFIDENCE_INTERVAL_95_STDS = 1.96f;


    @Override
    public String getName() {
        return "Anomalies (anomalies/all)";
    }

    @Override
    public float execute(List<WebDataFrame> data) {
        ArrayList<Float> volumes = new ArrayList<>();
        ArrayList<Float> priceDiffs = new ArrayList<>();

        for (WebDataFrame dataFrame : data) {
            volumes.add(dataFrame.volume);
            priceDiffs.add(dataFrame.high - dataFrame.low);
        }

        LinearRegressionResult result = trainLinearRegression(volumes, priceDiffs);

        ArrayList<Float> residuals = new ArrayList<>();
        float meanRes = 0;

        for (int i = 0; i < data.size(); i++) {
            float res = priceDiffs.get(i) - (result.a * volumes.get(i) + result.b);

            residuals.add(res);
            meanRes += res;
        }

        meanRes /= data.size();

        float std = calculateStandardDeviation(residuals, meanRes);
        int anomaliesCount = calculateNumberOfAnomalies(residuals, meanRes, std);

        return (float) anomaliesCount / (float) data.size();
    }


    private LinearRegressionResult trainLinearRegression(List<Float> X, List<Float> Y) {
        float a = 0, b = 0;

        Logger.debug("Starting MSE: " + calculateMSE(X, Y, a, b));

        for (int epoch = 0; epoch < EPOCH_COUNT; epoch++) {
            float grad_a = 0;
            float grad_b = 0;

            for (int i = 0; i < X.size(); i++) {
                float x = X.get(i);
                float y = Y.get(i);
                float res = y - (a * x + b);

                grad_a += 2 * res * (-x);
                grad_b += 2 * res * (-1);
            }

            grad_a /= X.size();
            grad_b /= X.size();

            a -= grad_a * LEARNING_RATE;
            b -= grad_b * LEARNING_RATE;
        }

        Logger.debug("Ending MSE: " + calculateMSE(X, Y, a, b));
        Logger.debug("a: " + a);
        Logger.debug("b: " + b);

        return new LinearRegressionResult(a, b);
    }

    private float calculateMSE(List<Float> X, List<Float> Y, float a, float b) {
        float mse = 0;

        for (int i = 0; i < X.size(); i++) {
            float x = X.get(i);
            float y = Y.get(i);
            float res = y - (a * x + b);

            mse += res * res;
        }

        mse /= X.size();

        return mse;
    }

    private float calculateStandardDeviation(List<Float> X, float mean) {
        float std = 0;

        for (float x : X) {
            float d = x - mean;
            std += d * d;
        }

        std /= X.size();
        std = (float) Math.sqrt(std);

        return std;
    }

    private int calculateNumberOfAnomalies(List<Float> X, float mean, float std) {
        int anomalies = 0;

        for (float x : X) {
            float z = (x - mean) / std;

            if (Math.abs(z) > CONFIDENCE_INTERVAL_95_STDS) {
                anomalies++;
            }
        }

        return anomalies;
    }


    private static class LinearRegressionResult {
        public float a;
        public float b;

        public LinearRegressionResult(float a, float b) {
            this.a = a;
            this.b = b;
        }
    }
}