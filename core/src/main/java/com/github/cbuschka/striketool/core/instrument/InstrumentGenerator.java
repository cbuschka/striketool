package com.github.cbuschka.striketool.core.instrument;

import com.github.cbuschka.striketool.core.alesis.instrument.Instrument;
import com.github.cbuschka.striketool.core.alesis.instrument.Range;
import com.github.cbuschka.striketool.core.alesis.instrument.SampleMapping;
import com.github.cbuschka.striketool.core.io.SampleReader;
import com.github.cbuschka.striketool.core.processing.Sample;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;

@AllArgsConstructor
public class InstrumentGenerator {
    @SneakyThrows
    public Instrument generate(InstrumentSpec instrumentSpec) {
        Instrument instrument = new Instrument(instrumentSpec.name);
        for (SoundMappingSpec soundMappingSpec : instrumentSpec.mappings) {
            Range range = new Range(soundMappingSpec.range.min, soundMappingSpec.range.max);
            SampleMapping sampleMapping = new SampleMapping(range);
            for (SoundSpec soundSpec : soundMappingSpec.sounds) {
                Sample sample = toSample(soundSpec);
                sampleMapping.samples.add(sample);
            }
            instrument.mappings.add(sampleMapping);
        }

        return instrument;
    }

    private Sample toSample(SoundSpec soundSpec) throws IOException {
        try (SampleReader rd = new SampleReader(soundSpec.file.openInputStream())) {
            return rd.readSample();
        }
    }
}