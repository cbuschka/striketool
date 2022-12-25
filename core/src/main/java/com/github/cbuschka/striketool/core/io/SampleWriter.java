package com.github.cbuschka.striketool.core.io;

import com.github.cbuschka.striketool.core.processing.Sample;

import java.io.IOException;
import java.io.OutputStream;

public class SampleWriter implements AutoCloseable {
    private OutputStream out;

    public SampleWriter(OutputStream out) {
        this.out = out;
    }

    public void write(Sample sample) throws IOException {
        WavFileWriter out = WavFileWriter.newWavFile(this.out, sample.getNumChannels(), sample.getNumFrames(), 16, sample.sampleRate);
        out.writeFrames(sample.frames, sample.getNumFrames());
        out.close();
        this.out = null;
    }

    @Override
    public void close() throws Exception {
        if (this.out != null) {
            out.close();
        }
    }
}
