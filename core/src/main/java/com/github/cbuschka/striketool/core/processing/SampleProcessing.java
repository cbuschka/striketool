package com.github.cbuschka.striketool.core.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SampleProcessing {
    private Supplier<Sample> sampleSupplier;

    private List<Function<Sample, Sample>> functions = new ArrayList<>();

    private SampleProcessing(Supplier<Sample> sampleSupplier) {
        this.sampleSupplier = sampleSupplier;
    }

    public static SampleProcessing process(Supplier<Sample> sampleSupplier) {
        return new SampleProcessing(sampleSupplier);
    }


    public static SampleProcessing process(Sample sample) {
        return process(() -> sample);
    }

    public SampleProcessing with(Function<Sample, Sample> function) {
        this.functions.add(function);
        return this;
    }

    public SampleProcessing copy() {
        return with(new CopySampleOp());
    }

    public SampleProcessing normalize(NormalizeSampleOp.Algorithm algorithm) {
        return with(new NormalizeSampleOp(algorithm));
    }

    public SampleProcessing monoToStereo() {
        return with(new ToStereoSampleOp());
    }

    public SampleProcessing mixedWith(Sample other) {
        return with(new MixSampleOp(other));
    }

    public SampleProcessing amplify(double factor) {
        return with(new AmplifySampleOp(factor));
    }

    public Sample get() {
        Sample sample = this.sampleSupplier.get();
        for (Function<Sample, Sample> function : this.functions) {
            sample = function.apply(sample);
        }
        return sample;
    }
}
