package com.newsscraper.ui;

import com.newsscraper.data.DataManager;
import com.newsscraper.data.WebSource;
import com.newsscraper.exporters.Exporter;
import com.newsscraper.logging.Logger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MainPanel {

    private final JFrame window;
    private final List<WebSource> sources;
    private final List<Exporter> exporters;
    private WebSource currentSource;

    public MainPanel(List<WebSource> sources, List<Exporter> exporters) {
        window = new JFrame();
        window.setTitle("News Scraper");
        window.setSize(500, 400);
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
        this.exporters = exporters;

        setupButton();
        setupPagesReadText();
        setupSources();
        setupExporters();

        window.setLayout(null);
        window.setVisible(true);

        Logger.info("Initialized Main UI Panel");
    }

    private void setupButton() {
        final int buttonWidth = 250;

        JButton startButton = new JButton("START");

        startButton.setBounds(window.getWidth() / 2 - buttonWidth / 2, 250, buttonWidth, 50);
        startButton.addActionListener(e -> {
            Logger.info("Starting News Scraper...");

            DataManager.getInstance().initRepo();

            currentSource.downloadFromWeb();

            DataManager.getInstance().flushRepo();
        });

        window.add(startButton);

        Logger.info("Start button has been set up");
    }

    private void setupPagesReadText() {
        new PagesReadPanel(window);
    }

    private void setupSources() {
        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < sources.size(); i++) {
            final int btnIndex = i;

            JRadioButton radioButton = new JRadioButton(sources.get(btnIndex).getName());
            radioButton.setBounds(10, i * 40, 250, 60);
            radioButton.addActionListener(e -> {
                Logger.debug("Radio Button '" + sources.get(btnIndex).getName() + "' selected");

                currentSource = sources.get(btnIndex);
            });

            if (i == 0) {
                radioButton.doClick();
            }

            buttonGroup.add(radioButton);
            window.add(radioButton);
        }

        Logger.info("Setup of sources buttons complete!");
    }

    private void setupExporters() {
        for (int i = 0; i < exporters.size(); i++) {
            final int btnIndex = i;

            JCheckBox checkbox = new JCheckBox(exporters.get(btnIndex).getName());
            checkbox.setBounds(300, i * 40, 250, 60);
            checkbox.addActionListener(e -> {
                Logger.debug("Checkbox '" + exporters.get(btnIndex).getName() + "' selected: " + checkbox.isSelected());

                if (checkbox.isSelected()) {
                    DataManager.getInstance().addObserver(exporters.get(btnIndex));
                } else {
                    DataManager.getInstance().removeObserver(exporters.get(btnIndex));
                }
            });
            checkbox.doClick();

            window.add(checkbox);
        }

        Logger.info("Setup of exporters buttons complete!");
    }
}
