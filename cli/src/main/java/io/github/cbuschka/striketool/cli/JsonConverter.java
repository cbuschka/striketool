package io.github.cbuschka.striketool.cli;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.cbuschka.strike4j.instrument.Instrument;
import io.github.cbuschka.strike4j.instrument.InstrumentReader;
import io.github.cbuschka.strike4j.instrument.InstrumentWriter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Component
public class JsonConverter {
    private static final String VERSION = "io.github.cbuschka.striketool.instrument/1.0";

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, true);

    @SneakyThrows
    public void toJson(File inputFile, File outputFile) {
        try (InstrumentReader inRd = new InstrumentReader(inputFile.getPath(), new FileInputStream(inputFile))) {
            Instrument instrument = inRd.read();
            ObjectNode instrumentJson = objectMapper.valueToTree(instrument);
            instrumentJson.put("version", VERSION);
            byte[] json = objectMapper.writeValueAsBytes(instrumentJson);
            try (FileOutputStream out = new FileOutputStream(outputFile)) {
                out.write(json);
            }
        }
    }

    @SneakyThrows
    public void fromJson(File inputFile, File outputFile) {
        try (FileInputStream in = new FileInputStream(inputFile)) {
            ObjectNode instrumentJson = (ObjectNode) objectMapper.readerFor(Instrument.class).readTree(in);
            JsonNode versionPropNode = instrumentJson.get("version");
            if (versionPropNode.isTextual() && !VERSION.equals(versionPropNode.asText())) {
                throw new IllegalArgumentException("Expected version " + VERSION + "; was " + versionPropNode);
            }
            instrumentJson.remove("version");
            Instrument instrument = objectMapper.readerFor(Instrument.class).readValue(instrumentJson);
            try (InstrumentWriter inWr = new InstrumentWriter(new FileOutputStream(outputFile))) {
                inWr.write(instrument);
            }
        }
    }
}
