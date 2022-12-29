package com.github.cbuschka.striketool.ui.components.waveform;

import com.github.cbuschka.striketool.core.processing.Sample;

public class SampleWaveformModel implements WaveformModel {

    private Sample sample;

    public SampleWaveformModel(Sample sample) {
        this.sample = sample;
    }

    @Override
    public int getLength() {
        return sample.getNumFrames();
    }

    @Override
    public double getValue(int index) {
        return sample.getFrame(0, index);
    }
}