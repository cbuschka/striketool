package com.github.cbuschka.striketool.core.alesis.instrument;

import com.github.cbuschka.striketool.core.processing.Sample;

import java.util.ArrayList;
import java.util.List;

public class Instrument {

    public String name;

    public List<SampleMapping> mappings = new ArrayList<>();

    public Instrument(String name) {
        this.name = name;
    }

    public List<Sample> getSamplesFor(int level) {
        List<Sample> samples = new ArrayList<>();
        for (SampleMapping mapping : mappings) {
            if (mapping.range.includes(level)) {
                samples.addAll(mapping.samples);
            }
        }

        return samples;
    }
}
