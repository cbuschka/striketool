package com.github.cbuschka.striketool.core.processing;

import lombok.SneakyThrows;

import java.util.function.Function;

public class ToMonoSampleOp implements Function<Sample, Sample> {

    @SneakyThrows
    @Override
    public Sample apply(Sample sample) {
        if (sample.getNumChannels() == 2) {
            return sample;
        }

        double[][] newFrames = new double[][]{
                new double[sample.frames[0].length]};
        for (int frameIndex = 0; frameIndex < sample.getNumFrames(); ++frameIndex) {
            newFrames[0][frameIndex] = sample.frames[0][frameIndex];
        }

        return new Sample(newFrames, sample.sampleRate);
    }
}
