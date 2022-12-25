package com.github.cbuschka.striketool.core.player;

import com.github.cbuschka.striketool.core.processing.Sample;
import lombok.SneakyThrows;

import javax.sound.sampled.SourceDataLine;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class AudioBuffer {
    public static final int NUM_CHANNELS = 2;
    public static final int NUM_FRAMES_PER_CHUNK = 44100 / (1000 / 10); // 10 millis chunks

    private static class Chunk {
        final double[][] frames = new double[NUM_CHANNELS][NUM_FRAMES_PER_CHUNK];
        SamplePlayerListener startListener;
        SamplePlayerListener stopListener;

        public void clear() {
            for (int channelIndex = 0; channelIndex < frames.length; ++channelIndex) {
                Arrays.fill(frames[channelIndex], 0.0d);
            }
            startListener = null;
            stopListener = null;
        }

        @SneakyThrows
        public void copyInto(SourceDataLine sourceDataLine) {
            if (stopListener != null) {
                stopListener.sampleStopped();
            }


            if (startListener != null) {
                startListener.sampleStarted();
            }

            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream(frames.length * frames[0].length * 4);
            DataOutputStream dataOut = new DataOutputStream(bytesOut);

            for (int frameIndex = 0; frameIndex < frames[0].length; ++frameIndex) {
                dataOut.writeInt((int) (Integer.MAX_VALUE * frames[0][frameIndex]));
                dataOut.writeInt((int) (Integer.MAX_VALUE * frames[1][frameIndex]));
            }
            dataOut.close();
            byte[] bytes = bytesOut.toByteArray();
            int count = sourceDataLine.write(bytes, 0, bytes.length);
            if (count != bytes.length) {
                throw new IllegalStateException("Buffer full!!!");
            }
        }
    }

    private final List<Chunk> chunks = new ArrayList<>();
    private final List<Chunk> freeChunks = new LinkedList<>();

    public AudioBuffer() {
        freeChunks.add(new Chunk());
    }

    @SneakyThrows
    public synchronized boolean copyChunkInto(SourceDataLine sourceDataLine) {
        if (chunks.isEmpty()) {
            Chunk chunk = new Chunk();
            chunk.clear();
            chunk.copyInto(sourceDataLine);
            return false;
        }

        int chunkInBytes = NUM_FRAMES_PER_CHUNK * 4 * NUM_CHANNELS;
        int available = sourceDataLine.available();
        boolean enqueudChunk = false;
        if (available >= chunkInBytes) {
            Chunk chunk = chunks.remove(0);
            chunk.copyInto(sourceDataLine);

            freeChunks.add(chunk);
            enqueudChunk = true;
        }

        // System.err.println("Enqueued chunk=" + enqueudChunk + ", audioBuffer.chunks=" + chunks.size() + ", chunk.size=" + chunkInBytes + ", line.bufSize=" + sourceDataLine.getBufferSize() + ", line.available=" + available);
        return true;
    }

    @SneakyThrows
    public synchronized void addSample(Sample sample, SamplePlayerListener listener) {

        if (sample.getNumChannels() != NUM_CHANNELS) {
            throw new IllegalArgumentException("Must have 2 channels.");
        }

        int chunkIndex = chunks.size();
        Chunk chunk = getChunk(chunkIndex);
        if (listener != null) {
            chunk.startListener = listener;
        }
        int chunkFrameIndex = 0;
        for (int frameIndex = 0; frameIndex < sample.getNumFrames(); ++frameIndex) {

            if (chunkFrameIndex == NUM_FRAMES_PER_CHUNK) {
                chunkIndex++;
                chunk = getChunk(chunkIndex);
                chunkFrameIndex = 0;
            }

            chunk.frames[0][chunkFrameIndex] = sample.frames[0][frameIndex];
            chunk.frames[1][chunkFrameIndex] = sample.frames[1][frameIndex];
            chunkFrameIndex++;
        }

        if (listener != null) {
            chunk = getChunk(chunks.size());
            chunk.stopListener = listener;
        }
    }

    private Chunk getChunk(int i) {
        while (i >= chunks.size()) {
            Chunk chunk;
            if (freeChunks.isEmpty()) {
                chunk = new Chunk();
            } else {
                chunk = freeChunks.remove(0);
                chunk.clear();
            }

            chunks.add(chunk);
        }

        return chunks.get(i);
    }
}
