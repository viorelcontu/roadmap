package com.endava.practice.roadmap.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    ObjectWriter objectWriter() {
        return new ObjectMapper().writer().withDefaultPrettyPrinter();
    }
}
