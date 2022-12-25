package com.github.cbuschka.striketool.core.alesis.instrument;

import com.github.cbuschka.striketool.core.processing.Sample;

import java.util.ArrayList;
import java.util.List;

public class SampleMapping {

    public Range range;

    public List<Sample> samples = new ArrayList<>();

    public SampleMapping(Range range) {
        this.range = range;
    }
}
