package com.github.cbuschka.striketool.core.processing;

import lombok.SneakyThrows;

import java.io.IOException;
import java.util.function.Function;

public class ToStereoSampleOp implements Function<Sample, Sample> {

    @SneakyThrows
    @Override
    public Sample apply(Sample sample) {
        if (sample.getNumChannels() == 2) {
            return sample;
        }
        if (sample.getNumChannels() != 1) {
            throw new IOException("For mono-to-stereo a sample with a single channel is expected.");
        }

        double[][] newFrames = new double[][]{
                sample.frames[0],
                new double[sample.frames[0].length]};
        for (int frameIndex = 0; frameIndex < sample.getNumFrames(); ++frameIndex) {
            newFrames[1][frameIndex] = newFrames[0][frameIndex];
        }
        sample.frames = newFrames;

        return sample;
    }
}
