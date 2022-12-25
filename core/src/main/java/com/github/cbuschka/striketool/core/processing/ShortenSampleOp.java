package com.github.cbuschka.striketool.core.processing;

import java.util.function.Function;

public class ShortenSampleOp implements Function<Sample, Sample> {
    private final int millis;

    public ShortenSampleOp(int millis) {
        this.millis = millis;
    }

    @Override
    public Sample apply(Sample sample) {

        int maxFrameCount = (int) (sample.sampleRate / 1000.0d * millis);

        Sample shortenedSample = new Sample(sample.getNumChannels(), Math.min(sample.getNumFrames(), maxFrameCount), sample.sampleRate);
        for (int channelIndex = 0; channelIndex < sample.getNumChannels(); ++channelIndex) {
            System.arraycopy(sample.frames[channelIndex], 0, shortenedSample.frames[channelIndex], 0, maxFrameCount);
        }

        return shortenedSample;
    }
}
