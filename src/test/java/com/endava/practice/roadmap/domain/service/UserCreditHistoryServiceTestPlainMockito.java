package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.service.internalservice.CongratulationService;
import com.endava.practice.roadmap.domain.service.internalservice.CreditHistoryService;
import com.endava.practice.roadmap.domain.service.internalservice.UserCreditHistoryService;
import com.endava.practice.roadmap.domain.service.internalservice.UserService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
@ExtendWith(MockitoExtension.class)
class UserCreditHistoryServiceTestPlainMockito {

    @Mock
    private UserService userService;

    @Mock
    private CreditHistoryService creditHistoryService;

    @Mock
    private CongratulationService congratulationService;

    @InjectMocks
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