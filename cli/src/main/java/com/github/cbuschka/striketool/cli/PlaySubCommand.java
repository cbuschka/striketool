package com.github.cbuschka.striketool.cli;

import com.github.cbuschka.striketool.core.alesis.instrument.Instrument;
import com.github.cbuschka.striketool.core.instrument.InstrumentGenerator;
import com.github.cbuschka.striketool.core.instrument.InstrumentSpec;
import com.github.cbuschka.striketool.core.player.SamplePlayer;
import com.github.cbuschka.striketool.core.player.SamplePlayerListener;
import com.github.cbuschka.striketool.core.processing.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PlaySubCommand {
    private File instrumentFile;

    public PlaySubCommand(File instrumentFile) {
        this.instrumentFile = instrumentFile;
    }

    @SneakyThrows
    public void run() {

        InstrumentSpec instrumentSpec = InstrumentSpec.load(instrumentFile);

        log.info("Instrument loaded from {}.", instrumentFile);


        InstrumentGenerator generator = new InstrumentGenerator();
        Instrument instrument = generator.generate(instrumentSpec);

        SamplePlayer samplePlayer = new SamplePlayer();
        samplePlayer.start();

        Map<Integer, List<Sample>> samplesByMidiLevel = generateSamplesByMidiLevel(instrument);

        for (Map.Entry<Integer, List<Sample>> entry : samplesByMidiLevel.entrySet()) {

            List<Sample> samples = entry.getValue();
            Integer midiLevel = entry.getKey();
            log.info("Playing for {} ({} sample(s) available).", midiLevel, samples.size());

            for (Sample sample : samples) {
                samplePlayer.addSample(sample, new SamplePlayerListener() {
                    @Override
                    public void sampleStarted() {
                        System.err.println("Sample started.");
                    }

                    @Override
                    public void sampleStopped() {
                        System.err.println("Sample stopped.");
                    }
                });
            }
        }

        samplePlayer.waitForPlaying();
        samplePlayer.waitForNotPlaying();
        samplePlayer.stop();

    }

    private static Map<Integer, List<Sample>> generateSamplesByMidiLevel(Instrument instrument) {
        Map<Integer, List<Sample>> samplesByMidiLevel = new LinkedHashMap<>();
        final int SLOTS_N = 8;
        for (int i = 1; i < SLOTS_N; ++i) {
            double level = i == SLOTS_N - 1 ? 1 : 1 / (double) SLOTS_N * i;
            int midiLevel = (int) (level * 127);
            List<Sample> samples = instrument.getSamplesFor(midiLevel);
            if (samples.isEmpty()) {
                log.error("No sample(s) for {}.", midiLevel);
            }

            List<Sample> playableSamples = new ArrayList<>();
            for (Sample sample : samples) {
                CopySampleOp copyOp = new CopySampleOp();
                sample = copyOp.apply(sample);

                ToStereoSampleOp toStereoSampleOp = new ToStereoSampleOp();
                sample = toStereoSampleOp.apply(sample);

                NormalizeSampleOp normalizeSampleOp = new NormalizeSampleOp(NormalizeSampleOp.Algorithm.PEAK);
                sample = normalizeSampleOp.apply(sample);

                int playMillis = 300;
                int fadeOutMillis = Math.min(playMillis / 25, 80);

                ShortenSampleOp shortenSampleOp = new ShortenSampleOp(playMillis + fadeOutMillis);
                sample = shortenSampleOp.apply(sample);

                FadeOutSampleOp fadeOutSampleOp = new FadeOutSampleOp(sample.lengthInMillis() - fadeOutMillis);
                sample = fadeOutSampleOp.apply(sample);

                ShortenSampleOp shortenSampleOp2 = new ShortenSampleOp(playMillis);
                sample = shortenSampleOp2.apply(sample);

                AmplifySampleOp amplifySampleOp = new AmplifySampleOp(level);
                sample = amplifySampleOp.apply(sample);

                playableSamples.add(sample);
            }
            samplesByMidiLevel.put(midiLevel, playableSamples);
        }
        return samplesByMidiLevel;
    }
}
