package com.github.cbuschka.striketool.core.alesis.drum_module.simulator;

import com.github.cbuschka.striketool.core.io.Resource;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SimulatorTest {

    Simulator simulator = new Simulator();

    @Test
    void test() {
        simulator.start();

        Resource root = simulator.getInternalRoot();
        List<Resource> resources = root.listResources();
        assertThat(resources).isNotEmpty();

        Optional<Resource> optInstruments = root.lookup("Instruments");
        assertThat(optInstruments).isPresent();
        Resource instruments = optInstruments.get();
        assertThat(instruments.exists()).isTrue();
        assertThat(instruments.getName()).isEqualTo("Instruments");
        assertThat(instruments.isFolder()).isTrue();
        System.err.println(resources);

        simulator.stop();
    }

}