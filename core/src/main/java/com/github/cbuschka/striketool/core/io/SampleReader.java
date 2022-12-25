package com.github.cbuschka.striketool.core.io;


import com.github.cbuschka.striketool.core.processing.Sample;

import java.io.IOException;
import java.io.InputStream;

public class SampleReader implements AutoCloseable {
    private WavFileReader in;

    public SampleReader(InputStream in) throws IOException {
        this.in = WavFileReader.openWavFile(in);
    }

    public Sample readSample() throws IOException {
        long numFrames = this.in.getNumFrames();
        if (numFrames > Integer.MAX_VALUE) {
            throw new IOException("Too many frames.");
        }
        int numChannels = in.getNumChannels();
        Sample sample = new Sample(numChannels, (int) numFrames, in.getSampleRate());
            this.in.readFrames(sample.frames, (int)numFrames);
        return sample;
    }

    @Override
    public void close() throws IOException {
        this.in.close();
    }
}
