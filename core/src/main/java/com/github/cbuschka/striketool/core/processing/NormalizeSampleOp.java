package com.github.cbuschka.striketool.core.processing;

import lombok.SneakyThrows;

import java.util.function.Function;

public class NormalizeSampleOp implements Function<Sample, Sample> {
    public enum Algorithm {
        PEAK, RMS;
    }

    private Algorithm algorithm;

    public NormalizeSampleOp(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public Sample apply(Sample sample) {

        switch (algorithm) {
            case PEAK:
                normalize(sample, NormalizeSampleOp::findAbsMaxOverChannels);
                break;
            case RMS:
                normalize(sample, NormalizeSampleOp::findAbsRMSOverChannels);
                break;
        }

        return sample;
    }

    @SneakyThrows
    private void normalize(Sample sample, Function<Sample, Double> f) {
        double maxAbsFrameValue = f.apply(sample);
        double normalizeFactor = 1.0d / maxAbsFrameValue;

        for (int channelIndex = 0; channelIndex < sample.getNumChannels(); channelIndex++) {
            for (int frameIndex = 0; frameIndex < sample.getNumFrames(); ++frameIndex) {
                double frameValue = sample.frames[channelIndex][frameIndex];
                double newFrameValue = frameValue * normalizeFactor;
                if (newFrameValue >= 0) {
                    newFrameValue = Math.min(newFrameValue, 1d);
                } else {
                    newFrameValue = Math.max(newFrameValue, -1d);
                }
                sample.frames[channelIndex][frameIndex] = newFrameValue;
            }
        }
    }

    private static double findAbsMaxOverChannels(Sample sample) {
        double maxAbsFrameValue = 0;
        for (int channelIndex = 0; channelIndex < sample.getNumChannels(); channelIndex++) {
            for (int frameIndex = 0; frameIndex < sample.getNumFrames(); ++frameIndex) {
                double frameValue = sample.frames[channelIndex][frameIndex];
                maxAbsFrameValue = Math.max(maxAbsFrameValue, Math.abs(frameValue));
            }
        }
        return maxAbsFrameValue;
    }

    private static double findAbsRMSOverChannels(Sample sample) {
        return RMS.findAbsRMSOverChannels(sample.frames);
    }
}
