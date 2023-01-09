package com.github.cbuschka.striketool.core.alesis.drum_module;

public interface DrumModuleListener {
    default void available(DrumModuleEvent event) {
    }

    default void unavailable(DrumModuleEvent event) {
    }
}
