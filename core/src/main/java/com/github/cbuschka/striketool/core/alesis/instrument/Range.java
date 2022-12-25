package com.github.cbuschka.striketool.core.alesis.instrument;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
public class Range {
    public int min;

    public int max;

    public boolean includes(int level) {
        return min <= level && level <= max;
    }
}
