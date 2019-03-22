package com.endava.practice.roadmap.component;

import lombok.Getter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class BaseTest {

    @LocalServerPort
    private int randomServerPort;

    @Getter
    private String usersURL;

    @PostConstruct
    private void init() {
        String baseURI = "http://localhost:" + randomServerPort;
        usersURL = baseURI + "/users/";
    }
}
