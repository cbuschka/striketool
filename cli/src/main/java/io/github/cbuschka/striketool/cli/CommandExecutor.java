package io.github.cbuschka.striketool.cli;

import lombok.AllArgsConstructor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@AllArgsConstructor
public class CommandExecutor implements CommandLineRunner {
    private JsonConverter jsonConverter;

    @Override
    public void run(String... args) throws Exception {
        Options options = new Options();
        options.addRequiredOption("i", "input", true, "an input file");
        options.addRequiredOption("o", "output", true, "an output file");
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);
        String input = commandLine.getOptionValue('i');
        File inputFile = new File(input);
        String output = commandLine.getOptionValue('o');
        File outputFile = new File(output);
        List<String> argList = commandLine.getArgList();
        if (argList.size() == 1 && "tojson".equals(argList.get(0))) {
            jsonConverter.toJson(inputFile, outputFile);
        } else if (argList.size() == 1 && "fromjson".equals(argList.get(0))) {
            jsonConverter.fromJson(inputFile, outputFile);
        } else {
            throw new IllegalArgumentException("Only single command allowed: tosin|tojson");
        }
    }
}
