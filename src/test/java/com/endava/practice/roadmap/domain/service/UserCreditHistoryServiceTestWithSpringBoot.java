package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.service.internalservice.UserCreditHistoryService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import lombok.val;

import static com.endava.practice.roadmap.util.TestUsers.CLIENT_EXISTING;
import static com.endava.practice.roadmap.util.TestUtils.getUserHistoryForClient;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("congratulations-test")
class UserCreditHistoryServiceTestWithSpringBoot {

    @Autowired
    private UserCreditHistoryService userCreditHistoryService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testMakePaymentSuccess() {
        val clientUser = CLIENT_EXISTING.buildUser();

        userRepository.save(clientUser);

        val result = userCreditHistoryService.supplyBalance(
            clientUser,
            BigDecimal.valueOf(50));

        val expectedClient = getUserHistoryForClient();

        assertAll(
            () -> assertEquals(expectedClient.getUsername(), result.getUsername()),
            () -> assertEquals(expectedClient.getNickname(), result.getNickname()),
            () -> assertEquals(expectedClient.getAmount(), result.getAmount()),
            () -> assertEquals(expectedClient.getEmail(), result.getEmail()),
            () -> assertEquals(BigDecimal.valueOf(150), result.getBalance()),
            () -> assertNotNull(result.getPaymentDate()));
    }
}