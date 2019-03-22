package com.endava.practice.roadmap;

import com.endava.practice.roadmap.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = TestConfig.class)
public class RoadmapApplicationTests {

    @Test
    public void contextLoads() {
    }
}

