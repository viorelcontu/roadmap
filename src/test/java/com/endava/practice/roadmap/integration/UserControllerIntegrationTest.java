package com.endava.practice.roadmap.integration;

import com.endava.practice.roadmap.config.TestConfig;
import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.service.internalservice.UserCreditHistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.URISyntaxException;

import lombok.val;

import static com.endava.practice.roadmap.util.TestUsers.CLIENT_EXISTING;
import static com.endava.practice.roadmap.util.TestUtils.createURLWithPort;
import static com.endava.practice.roadmap.util.TestUtils.getUsersDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("congratulations-test")
public class UserControllerIntegrationTest {
    @Autowired
    private UserCreditHistoryService userCreditHistoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testGetAllUsersReturnsOk() throws JsonProcessingException, URISyntaxException, JSONException {
        userRepository.deleteAll();

        val clientUser = CLIENT_EXISTING.buildUser();

        userRepository.save(clientUser);

        val response = restTemplate.getForEntity(new URI(createURLWithPort("/users", port)), String.class);

        val expected = getUsersDto();

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), response.getBody(), JSONCompareMode.STRICT);
    }
}
