package com.github.cbuschka.striketool.ui.dialogs.sample_editor;

import com.github.cbuschka.striketool.core.processing.Sample;
import com.github.cbuschka.striketool.ui.Session;
import com.github.cbuschka.striketool.ui.components.waveform.SampleWaveformModel;
import com.github.cbuschka.striketool.ui.components.waveform.Waveform;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SampleEditor extends JPanel {

    private JToolBar toolBar;
    private Session session;

    private Sample sample;

    @SneakyThrows
    public SampleEditor(Session session) {
        this.session = session;

        initUI();
    }

    private void initUI() throws IOException {
        this.toolBar = new JToolBar();
        this.toolBar.setFloatable(false);
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (sample != null) {
                    session.getSamplePlayer().addSample(sample);
                }
            }
        });
        this.toolBar.add(playButton);
        setLayout(new BorderLayout());
        add(BorderLayout.NORTH, this.toolBar);

        sample = Sample.from(getClass().getResourceAsStream("/subtle-reverb-snare-drum-sound-a-key-01-Kb6.wav"));

        add(BorderLayout.CENTER, new Waveform(new SampleWaveformModel(sample)));
    }
}
