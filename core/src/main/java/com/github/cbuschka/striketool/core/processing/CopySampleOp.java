package com.github.cbuschka.striketool.core.processing;

import java.util.function.Function;

public class CopySampleOp implements Function<Sample, Sample> {

    @Override
    public Sample apply(Sample sample) {
        Sample newSample = new Sample(sample.getNumChannels(), sample.getNumFrames(), sample.sampleRate);
        for (int channelIndex = 0; channelIndex < sample.getNumChannels(); ++channelIndex) {
            for (int frameIndex = 0; frameIndex < sample.getNumFrames(); ++frameIndex) {
                newSample.frames[channelIndex][frameIndex] = sample.frames[channelIndex][frameIndex];
            }
        }
        return newSample;
    }
}
