package com.github.cbuschka.striketool.core.instrument;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SoundMappingSpec {
    @JsonProperty
    public RangeSpec range;

    @JsonProperty
    public List<SoundSpec> sounds;

}
