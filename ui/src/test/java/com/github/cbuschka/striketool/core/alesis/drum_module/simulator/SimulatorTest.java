package com.github.cbuschka.striketool.core.alesis.drum_module.simulator;

import org.junit.jupiter.api.Test;
import striketool.backend.module.Simulator;

class SimulatorTest {

    Simulator simulator = new Simulator();

    @Test
    void test() {
        simulator.start();

        simulator.stop();
    }

}