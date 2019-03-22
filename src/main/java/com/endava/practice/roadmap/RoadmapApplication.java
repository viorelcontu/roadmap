package com.endava.practice.roadmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"com.endava.practice.roadmap.configuration",
	"com.endava.practice.roadmap.persistence",
	"com.endava.practice.roadmap.service",
	"com.endava.practice.roadmap.web.controller"
})
public class RoadmapApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoadmapApplication.class, args);
	}

}