package com.github.cbuschka.striketool.core.instrument;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cbuschka.striketool.core.io.Resource;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.List;

public class InstrumentSpec {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static InstrumentSpec load(Resource file) {
        try (InputStream in = file.openInputStream()) {
            InstrumentSpec instrumentSpec = objectMapper.readerFor(InstrumentSpec.class).readValue(in);
            instrumentSpec.file = file;
            return instrumentSpec;
        }
    }

    @JsonIgnore
    public Resource file;

    @JsonProperty
    public String name;

    @JsonProperty
    public List<SoundMappingSpec> mappings;

}
