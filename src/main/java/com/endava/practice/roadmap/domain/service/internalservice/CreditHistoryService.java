package com.endava.practice.roadmap.domain.service.internalservice;

import com.endava.practice.roadmap.domain.dao.CreditHistoryRepository;
import com.endava.practice.roadmap.domain.model.entities.CreditHistory;
import com.endava.practice.roadmap.domain.model.entities.User;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class CreditHistoryService {

    private final CreditHistoryRepository creditHistoryRepository;

    public CreditHistory substractMoneyFromUser(User user, BigDecimal amount) {
        val userBalance = user.getBalance();

        if (userBalance.compareTo(amount) != 0) {
            throw new RuntimeException("Insufficient balance to complete transaction");
        }

        CreditHistory creditHistoryToSave = getCreditHistory(user, amount);
        return creditHistoryRepository.save(creditHistoryToSave);
    }

    private CreditHistory getCreditHistory(User user, BigDecimal amount) {
        return CreditHistory.builder()
            .client(user)
            .amount(amount)
            .paymentDate(LocalDateTime.now())
            .build();
    }

    public CreditHistory supplyToUser(User user, BigDecimal amount){
        return creditHistoryRepository.save(getCreditHistory(user, amount));
    }
}
