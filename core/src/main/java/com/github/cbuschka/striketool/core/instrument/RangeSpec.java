package com.github.cbuschka.striketool.core.instrument;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RangeSpec {
    @JsonProperty
    public int min;

    @JsonProperty
    public int max;
}
