package com.endava.practice.roadmap.domain.model.internal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserHistoryDto {
    private String username;
    private String nickname;
    private String email;
    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private BigDecimal balance;
}
