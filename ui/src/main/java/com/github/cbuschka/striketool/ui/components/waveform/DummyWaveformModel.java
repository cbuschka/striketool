package com.github.cbuschka.striketool.ui.components.waveform;

public class DummyWaveformModel implements WaveformModel {
    @Override
    public int getLength() {
        return 720;
    }

    @Override
    public double getValue(int index) {
        return Math.sin(Math.toRadians(index));
    }
}
