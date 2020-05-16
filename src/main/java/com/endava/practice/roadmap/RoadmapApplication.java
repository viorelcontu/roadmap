package com.endava.practice.roadmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

@EnableCaching
@SpringBootApplication(scanBasePackages = {
        "com.endava.practice.roadmap.config",
        "com.endava.practice.roadmap.domain.service",
        "com.endava.practice.roadmap.domain.security",
        "com.endava.practice.roadmap.domain.dao",
    "com.endava.practice.roadmap.domain.converter",
        "com.endava.practice.roadmap.web"})
@PropertySource(value = "file:${ROADMAP_CONFIG_PATH:.}/roadmap.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:roadmap.properties", ignoreResourceNotFound = true)
public class RoadmapApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoadmapApplication.class, args);
    }

}