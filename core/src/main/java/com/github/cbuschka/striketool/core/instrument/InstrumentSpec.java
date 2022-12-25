package com.github.cbuschka.striketool.core.instrument;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;
import java.util.List;

public class InstrumentSpec {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static InstrumentSpec load(File file) {
        InstrumentSpec instrumentSpec = objectMapper.readerFor(InstrumentSpec.class).readValue(file);
        instrumentSpec.baseDir = file.getParentFile();
        return instrumentSpec;
    }

    @JsonIgnore
    public File baseDir;

    @JsonProperty
    public String name;

    @JsonProperty
    public List<SoundMappingSpec> mappings;

}
