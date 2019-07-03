package com.endava.practice.roadmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"com.endava.practice.roadmap.config",
    "com.endava.practice.roadmap.domain.service",
	"com.endava.practice.roadmap.domain.dao",
    "com.endava.practice.roadmap.domain.mapper",
	"com.endava.practice.roadmap.web"
})
public class RoadmapApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoadmapApplication.class, args);
	}

}