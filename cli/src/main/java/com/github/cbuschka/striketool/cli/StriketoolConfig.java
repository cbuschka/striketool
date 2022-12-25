package com.github.cbuschka.striketool.cli;

import com.github.cbuschka.striketool.core.Striketool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StriketoolConfig {
    @Bean
    public Striketool striketool() {
        return Striketool.newInstance();
    }
}
