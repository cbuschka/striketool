package com.github.cbuschka.striketool.core.processing;

import java.util.function.Function;

public class AmplifySampleOp implements Function<Sample, Sample> {

    private final double factor;

    public AmplifySampleOp(double factor) {
        this.factor = factor;
    }

    @Override
    public Sample apply(Sample sample) {
        for (int channelIndex = 0; channelIndex < sample.getNumChannels(); channelIndex++) {
            for (int frameIndex = 0; frameIndex < sample.getNumFrames(); ++frameIndex) {
                double frameValue = sample.frames[channelIndex][frameIndex];
                double newFrameValue = frameValue * factor;
                sample.frames[channelIndex][frameIndex] = Math.min(Math.max(-1d, newFrameValue), 1d);
            }
        }

        return sample;
    }
}
