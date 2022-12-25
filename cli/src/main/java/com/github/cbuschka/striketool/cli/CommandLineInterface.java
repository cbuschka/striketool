package com.github.cbuschka.striketool.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CommandLineInterface implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String filename = args[0];
        new PlaySubCommand(new File(filename)).run();
    }
}
