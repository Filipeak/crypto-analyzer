package com.cryptoanalyzer.ui;

import com.cryptoanalyzer.data.DataManager;
import com.cryptoanalyzer.data.WebDataSource;
import com.cryptoanalyzer.data.WebDataSourceStatus;
import com.cryptoanalyzer.services.DataService;
import com.cryptoanalyzer.logging.Logger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainPanel {

    private final JFrame window;
    private final WebDataSource[] sources;
    private final DataService[] services;
    private WebDataSource currentSource;

    public MainPanel(WebDataSource[] sources, DataService[] services) {
        window = new JFrame();
        window.setTitle("Crypto downloader");
        window.setSize(500, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Logger.info("Closing Window");
                Logger.shutdown();
            }
        });

        Logger.info("Created window");

        this.sources = sources;
        this.services = services;

        setupButton();
        setupPagesReadText();
        setupCandlestickPanel();
        setupSources();
        setupServices();

        window.setLayout(null);
        window.setVisible(true);

        Logger.info("Initialized Main UI Panel");
    }

    private void setupButton() {
        final int buttonWidth = 250;

        JButton startButton = new JButton("START");

        startButton.setBounds(window.getWidth() / 2 - buttonWidth / 2, 450, buttonWidth, 50);
        startButton.addActionListener(e -> {
            if (currentSource != null) {
                Logger.info("Starting News Scraper...");

                DataManager.getInstance().initRepo();
                WebDataSourceStatus status = currentSource.downloadFromWeb();
                DataManager.getInstance().flushRepo();

                showStatusPanel(status);
            } else {
                Logger.error("No source selected");
            }
        });

        window.add(startButton);

        Logger.info("Start button has been set up");
    }

    private void setupPagesReadText() {
        new CandlesReadPanel(window);
    }

    private void setupCandlestickPanel() {
        new CandlestickChartPanel();
    }

    private void setupSources() {
        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < sources.length; i++) {
            final int btnIndex = i;

            JRadioButton radioButton = new JRadioButton(sources[btnIndex].getName());
            radioButton.setBounds(10, i * 40, 250, 60);
            radioButton.addActionListener(e -> {
                Logger.debug("Radio Button '" + sources[btnIndex].getName() + "' selected");

                currentSource = sources[btnIndex];
            });

            if (i == 0) {
                radioButton.doClick();
            }

            buttonGroup.add(radioButton);
            window.add(radioButton);
        }

        Logger.info("Setup of sources buttons complete!");
    }

    private void setupServices() {
        for (int i = 0; i < services.length; i++) {
            final int btnIndex = i;

            JCheckBox checkbox = new JCheckBox(services[btnIndex].getName());
            checkbox.setBounds(300, i * 40, 250, 60);
            checkbox.addActionListener(e -> {
                Logger.debug("Checkbox '" + services[btnIndex].getName() + "' selected: " + checkbox.isSelected());

                if (checkbox.isSelected()) {
                    DataManager.getInstance().addObserver(services[btnIndex]);
                } else {
                    DataManager.getInstance().removeObserver(services[btnIndex]);
                }
            });
            checkbox.doClick();

            window.add(checkbox);
        }

        Logger.info("Setup of services buttons complete!");
    }

    private void showStatusPanel(WebDataSourceStatus status) {
        String messageText = switch (status) {
            case WebDataSourceStatus.SUCCESS -> "Success";
            case WebDataSourceStatus.FAILURE_NO_INTERNET -> "No internet connection";
            case WebDataSourceStatus.FAILURE_NO_DATA -> "No data! Unexpected error occurred.";
        };

        JOptionPane.showMessageDialog(window, messageText);
    }
}
