package com.github.cbuschka.striketool.core.processing;

import com.github.cbuschka.striketool.core.io.SampleReader;
import com.github.cbuschka.striketool.core.io.SampleWriter;
import lombok.SneakyThrows;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.*;

public class Sample {
    public long sampleRate;
    public double[][] frames;

    public int lengthInMillis() {
        return (int) (frames[0].length / (sampleRate / 1000.d));
    }

    public static Sample from(File file) throws IOException {
        return Sample.from(new FileInputStream(file));
    }

    @SneakyThrows
    public static Sample from(byte[] wavBytes) {
        return from(new ByteArrayInputStream(wavBytes));
    }

    public static Sample from(InputStream in) throws IOException {
        return new SampleReader(in).readSample();
    }

    public Sample(double[][] frames, long sampleRate) {
        this.sampleRate = sampleRate;
        this.frames = frames;
    }

    public Sample(int numChannels, int numFrames, long sampleRate) {
        this.sampleRate = sampleRate;
        this.frames = new double[numChannels][];
        for (int channelIndex = 0; channelIndex < numChannels; ++channelIndex) {
            this.frames[channelIndex] = new double[numFrames];
        }
    }

    public int getNumChannels() {
        return this.frames.length;
    }

    public int getNumFrames() {
        return this.frames[0].length;
    }

    @SneakyThrows
    public AudioInputStream getInputStream() {

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();

        for (int frameIndex = 0; frameIndex < this.frames[0].length; ++frameIndex) {
            for (int channelIndex = 0; channelIndex < this.frames.length; ++channelIndex) {
                double frameValue = this.frames[channelIndex][frameIndex];
                int x = (int) (frameValue * 32767.0);
                bytesOut.write((byte) x);
                bytesOut.write((byte) (x >>> 8));
            }
        }

        final boolean bigEndian = false;
        final boolean signed = true;

        AudioFormat format = new AudioFormat((float) this.sampleRate, 16, this.frames.length,
                signed, bigEndian);

        return new AudioInputStream(new ByteArrayInputStream(bytesOut.toByteArray()), format, this.frames[0].length);
    }

    @SneakyThrows
    public byte[] toWavBytes() {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        SampleWriter wr = new SampleWriter(bytesOut);
        wr.write(this);
        wr.close();
        return bytesOut.toByteArray();
    }

    public double getFrame(int chanNum, int frameIndex) {
        return frames[chanNum][frameIndex];
    }
}
