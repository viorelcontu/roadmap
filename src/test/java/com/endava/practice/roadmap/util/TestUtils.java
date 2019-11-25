package com.endava.practice.roadmap.util;

import com.endava.practice.roadmap.domain.model.entities.CreditHistory;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.domain.model.internal.UserHistoryDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import static com.endava.practice.roadmap.util.TestUsers.CLIENT_EXISTING;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {

    public static final LocalDateTime PAYMENT_DATE = LocalDateTime.MAX;
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(50);

    public static CreditHistory getCreditHistoryForUser(User user){
        return CreditHistory.builder()
            .client(user)
            .amount(AMOUNT)
            .paymentDate(PAYMENT_DATE)
            .build();
    }

    public static UserHistoryDto getUserHistoryForClient(){
        val clientUser = CLIENT_EXISTING.buildUser();

        return UserHistoryDto.builder()
            .nickname(clientUser.getNickname())
            .username(clientUser.getUsername())
            .email(clientUser.getEmail())
            .paymentDate(PAYMENT_DATE)
            .amount(AMOUNT)
            .balance(AMOUNT)
            .build();
    }

    public static List<UserDto> getUsersDto(){
        val clientUser = CLIENT_EXISTING.buildUser();

        val userDto = UserDto.builder()
            .nickname(clientUser.getNickname())
            .username(clientUser.getUsername())
            .email(clientUser.getEmail())
            .active(clientUser.getActive())
            .credits(clientUser.getCredits())
            .role(clientUser.getGroup().getRole())
            .build();
         return Collections.singletonList(userDto);
    }

    public static String createURLWithPort(String uri, int port) {
        return "http://localhost:" + port + uri;
    }

}
