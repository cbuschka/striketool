package com.github.cbuschka.striketool.core.processing;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.function.Supplier;

@AllArgsConstructor
public class GenerateBinauralBeatOp implements Supplier<Sample> {

    private int numChannels;

    @Override
    public Sample get() {
        final double sampleRate = 44100.0;
        final double frequency = 440;
        final double frequency2 = 90;
        final double amplitude = 1.0;
        final double seconds = 1.0;
        final double twoPiF = 2 * Math.PI * frequency;
        final double piF = Math.PI * frequency2;

        double[][] frames = new double[numChannels][];
        for (int channelIndex = 0; channelIndex < numChannels; ++channelIndex) {
            frames[channelIndex] = new double[(int) (seconds * sampleRate)];

            for (int i = 0; i < frames[channelIndex].length; i++) {
                double time = i / sampleRate;
                frames[channelIndex][i] = amplitude * Math.cos(piF * time) * Math.sin(twoPiF * time);
            }
        }

        return new Sample(frames, (long) sampleRate);
    }
}
