package com.github.cbuschka.striketool.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineInterface implements CommandLineRunner {
    @Autowired
    private PlaySubCommand playSubCommand;

    @Override
    public void run(String... args) throws Exception {
        String filename = args[0];
        playSubCommand.run(filename);
    }
}
