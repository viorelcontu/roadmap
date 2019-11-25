package com.endava.practice.roadmap.domain.service.internalservice;

import com.endava.practice.roadmap.domain.model.entities.CreditHistory;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.exceptions.ResourceNotFoundException;
import com.endava.practice.roadmap.domain.model.internal.UserHistoryDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class UserCreditHistoryService {
    private final UserService userService;
    private final CreditHistoryService creditHistoryService;
    private final CongratulationService congratulationService;

    @Transactional
    public UserHistoryDto makePayment(String userName, UUID token, BigDecimal amount){
        User user = userService.getUserByNameAndToken(userName, token)
            .orElseThrow(() -> ResourceNotFoundException.ofUserName(userName));

        user.setBalance(user.getBalance().subtract(amount));
        val credit = creditHistoryService.substractMoneyFromUser(user, amount);
        user = userService.saveUser(user);
        System.out.println(congratulationService.getMoneyWithDrawnMessage());
        return getUserHistoryDto(user, credit);
    }

    @Transactional
    public UserHistoryDto supplyBalance(User user, BigDecimal amount){
        user.setBalance(user.getBalance().add(amount));
        val credit = creditHistoryService.supplyToUser(user, amount);
        user = userService.saveUser(user);
        System.out.println(congratulationService.getMoneySuppliedMessage());
        return getUserHistoryDto(user, credit);
    }

    private UserHistoryDto getUserHistoryDto(User user, CreditHistory credit) {
        return UserHistoryDto.builder()
            .nickname(user.getNickname())
            .username(user.getUsername())
            .email(user.getEmail())
            .paymentDate(credit.getPaymentDate())
            .amount(credit.getAmount())
            .balance(user.getBalance())
            .build();
    }
}
