package com.cryptoanalyzer.ui;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataFrame;
import com.cryptoanalyzer.data.WebDataReceiver;
import com.cryptoanalyzer.logging.Logger;

import javax.swing.*;

public class CandlesReadPanel implements WebDataReceiver {

    private int counter;
    private final JLabel textLabel;

    public CandlesReadPanel(JFrame window) {
        final int textWidth = 250;

        textLabel = new JLabel("", SwingConstants.CENTER);
        textLabel.setBounds(window.getWidth() / 2 - textWidth / 2, 300, textWidth, 50);

        updateCounterText();

        window.add(textLabel);

        DataManager.getInstance().addObserver(this);

        Logger.info("Initialized text box");
    }

    @Override
    public void onBegin() {
        counter = 0;

        updateCounterText();
    }

    @Override
    public void onSetData(WebDataFrame frame) {
        counter++;

        updateCounterText();
    }

    @Override
    public void onEnd() {
    }

    private void updateCounterText() {
        textLabel.setText("Current Candles Read: " + counter);
    }
}
