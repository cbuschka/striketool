package com.github.cbuschka.striketool.core.processing;

import lombok.SneakyThrows;

import java.util.function.Function;

public class MixSampleOp implements Function<Sample, Sample> {
    private Sample added;

    public MixSampleOp(Sample added) {
        this.added = added;
    }

    @SneakyThrows
    public Sample apply(Sample baseline) {
        for (int channelIndex = 0; channelIndex < baseline.getNumChannels(); channelIndex++) {
            if (channelIndex < added.getNumChannels()) {
                for (int frameIndex = 0; frameIndex < baseline.getNumFrames(); ++frameIndex) {
                    double baselineFrameValue = baseline.frames[channelIndex][frameIndex];
                    double newFrameValue;
                    if (frameIndex < added.getNumFrames()) {
                        double addedFrameValue = added.frames[channelIndex][frameIndex];
                        // newFrameValue = baselineFrameValue + addedFrameValue - (baselineFrameValue * addedFrameValue);
                        newFrameValue = baselineFrameValue + addedFrameValue;
                    } else {
                        newFrameValue = baselineFrameValue;
                    }
                    if (newFrameValue >= 0) {
                        newFrameValue = Math.min(newFrameValue, 1d);
                    } else {
                        newFrameValue = Math.max(newFrameValue, -1d);
                    }
                    baseline.frames[channelIndex][frameIndex] = Math.min(Math.max(-1d, newFrameValue), 1d);
                }
            }
        }

        return baseline;
    }
}
