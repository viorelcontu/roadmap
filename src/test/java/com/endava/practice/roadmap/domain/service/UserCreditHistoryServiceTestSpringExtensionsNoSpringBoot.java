package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.config.UserCreditHistoryConfig;
import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.service.internalservice.UserCreditHistoryService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import lombok.val;

import static com.endava.practice.roadmap.util.TestUsers.CLIENT_EXISTING;
import static com.endava.practice.roadmap.util.TestUtils.getUserHistoryForClient;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserCreditHistoryConfig.class)
@TestPropertySource("classpath:application-congratulations-test.properties")
class UserCreditHistoryServiceTestSpringExtensionsNoSpringBoot {

    @Autowired
    private UserCreditHistoryService userCreditHistoryService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql(scripts = "/db/test-data.sql")
    public void testMakePaymentSuccess() {
        val clientUser = CLIENT_EXISTING.buildUser();

        val result = userCreditHistoryService.makePayment(
            clientUser.getUsername(),
            clientUser.getToken(),
            BigDecimal.valueOf(50));

        val expectedClient = getUserHistoryForClient();
        val actualUser = userRepository.findByUsernameIgnoreCase(clientUser.getUsername());

        assertAll(
            () -> assertEquals(expectedClient.getUsername(), result.getUsername()),
            () -> assertEquals(expectedClient.getNickname(), result.getNickname()),
            () -> assertEquals(expectedClient.getAmount(), result.getAmount()),
            () -> assertEquals(expectedClient.getEmail(), result.getEmail()),
            () -> assertNotNull(result.getPaymentDate()));

        assertTrue(actualUser.isPresent());
        assertEquals(result.getBalance(), actualUser.get().getBalance());
    }
}