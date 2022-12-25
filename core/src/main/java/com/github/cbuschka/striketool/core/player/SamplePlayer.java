package com.github.cbuschka.striketool.core.player;

import com.github.cbuschka.striketool.core.processing.Sample;
import lombok.SneakyThrows;

import javax.sound.sampled.*;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SamplePlayer {
    private final AudioBuffer audioBuffer = new AudioBuffer();

    private Mixer mixer;
    private SourceDataLine sourceDataLine;

    private final Object playingLock = new Object();
    private AtomicBoolean playing = new AtomicBoolean(false);

    private Thread streamThread;
    private AtomicBoolean stopped = new AtomicBoolean(false);

    @SneakyThrows
    private void init() {
        Mixer.Info[] aInfos = AudioSystem.getMixerInfo();
        for (int i = 0; i < aInfos.length; i++) {
            Mixer mixer = AudioSystem.getMixer(aInfos[i]);
            Line.Info lineInfo = new Line.Info(SourceDataLine.class);
            if (mixer.isLineSupported(lineInfo)) {
                this.mixer = mixer;
                this.sourceDataLine = (SourceDataLine) mixer.getLine(lineInfo);
                return;
            }
        }

        throw new NoSuchElementException();
    }

    @SneakyThrows
    public void start() {

        init();
        mixer.open();

        sourceDataLine.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.OPEN) {
                    System.err.println("open " + event);
                } else if (event.getType() == LineEvent.Type.CLOSE) {
                    System.err.println("close " + event);
                } else if (event.getType() == LineEvent.Type.START) {
                    System.err.println("start " + event);
                } else if (event.getType() == LineEvent.Type.STOP) {
                    System.err.println("stop " + event);
                } else {
                    System.err.println("unknown " + event);
                }
            }
        });
        sourceDataLine.open(new AudioFormat(44100, 32, 2, true, true), 1024 * 3 * 2);
        sourceDataLine.start();

        this.streamThread = new Thread(this::copyChunk);
        this.streamThread.setDaemon(true);
        this.streamThread.start();
    }

    private void copyChunk() {
        while (!stopped.get()) {
            try {
                int chunkSizeInBytes = AudioBuffer.NUM_FRAMES_PER_CHUNK * AudioBuffer.NUM_CHANNELS * 4;
                int dataInBuffer = (sourceDataLine.getBufferSize() - sourceDataLine.available());
                if (dataInBuffer < chunkSizeInBytes / 2) {

                    synchronized (audioBuffer) {
                        boolean copied = audioBuffer.copyChunkInto(sourceDataLine);
                        if (playing.get() != copied) {
                            playing.set(copied);
                            synchronized (playingLock) {
                                playingLock.notifyAll();
                            }
                        }
                        audioBuffer.wait(5);
                    }
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        sourceDataLine.close();
    }

    @SneakyThrows
    public void addSample(Sample sample) {
        audioBuffer.addSample(sample, null);
    }


    @SneakyThrows
    public void addSample(Sample sample, SamplePlayerListener samplePlayerListener) {
        audioBuffer.addSample(sample, samplePlayerListener);
    }

    public void waitForNotPlaying() throws InterruptedException {
        while (playing.get()) {
            synchronized (playingLock) {
                playingLock.wait(100);
            }
        }
    }

    public void stop() {

        this.stopped.set(true);
        this.streamThread.interrupt();
    }

    public void waitForPlaying() throws InterruptedException {
        while (!playing.get()) {
            synchronized (playingLock) {
                playingLock.wait(100);
            }
        }
    }
}
