package com.github.cbuschka.striketool.core.processing;

import java.util.function.Function;

public class FadeOutSampleOp implements Function<Sample, Sample> {
    private final int millis;

    public FadeOutSampleOp(int millis) {
        this.millis = millis;
    }

    @Override
    public Sample apply(Sample sample) {

        int maxFrameCount = (int) (sample.sampleRate / 1000.0d * millis);
        int frameCount = Math.min(maxFrameCount, sample.frames[0].length);

        for (int channelIndex = 0; channelIndex < sample.getNumChannels(); ++channelIndex) {
            for (int frameOffset = 0; frameOffset < frameCount; ++frameOffset) {
                int frameIndex = sample.frames[0].length - frameCount + frameOffset;
                double scale = ((double) frameCount - frameOffset) / (double) frameCount;
                sample.frames[channelIndex][frameIndex] = sample.frames[channelIndex][frameIndex] * scale;
            }
        }

        return sample;
    }
}
