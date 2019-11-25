package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.service.internalservice.CongratulationService;
import com.endava.practice.roadmap.domain.service.internalservice.CreditHistoryService;
import com.endava.practice.roadmap.domain.service.internalservice.UserCreditHistoryService;
import com.endava.practice.roadmap.domain.service.internalservice.UserService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.val;

import static com.endava.practice.roadmap.util.TestUsers.CLIENT_EXISTING;
import static com.endava.practice.roadmap.util.TestUtils.getCreditHistoryForUser;
import static com.endava.practice.roadmap.util.TestUtils.getUserHistoryForClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = UserCreditHistoryServiceConfig.class)
@Import(UserCreditHistoryService.class)
class UserCreditHistoryServiceTestSpringExtensions {

    @MockBean
    private UserService userService;

    @MockBean
    private CreditHistoryService creditHistoryService;

    @MockBean
    private CongratulationService congratulationService;

    @Autowired
    private UserCreditHistoryService userCreditHistoryService;

    @Test
    public void testMakePaymentSuccess() {
        val clientUser = CLIENT_EXISTING.buildUser();
        when(userService.getUserByNameAndToken(clientUser.getUsername(), clientUser.getToken()))
            .thenReturn(Optional.of(clientUser));
        when(creditHistoryService.substractMoneyFromUser(clientUser, BigDecimal.valueOf(50)))
            .thenReturn(getCreditHistoryForUser(clientUser));

        val clientToSave = clientUser.toBuilder().balance(BigDecimal.valueOf(50)).build();

        when(userService.saveUser(clientToSave)).thenReturn(clientToSave);
        when(congratulationService.getMoneyWithDrawnMessage()).thenReturn("Anything");

        val result = userCreditHistoryService.makePayment(
            clientUser.getUsername(),
            clientToSave.getToken(),
            BigDecimal.valueOf(50));

        verify(congratulationService).getMoneyWithDrawnMessage();
        assertEquals(getUserHistoryForClient(), result);
    }
}