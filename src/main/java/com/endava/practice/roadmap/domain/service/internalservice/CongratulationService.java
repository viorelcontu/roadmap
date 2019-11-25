package com.endava.practice.roadmap.domain.service.internalservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

import static lombok.AccessLevel.PACKAGE;

@Service
@Getter
@Setter(PACKAGE)
public class CongratulationService {

    @Value("${welcome.messages}")
    private String welcomeMessages;

    @Value("${congratulations.money.supplied}")
    private String moneySuppliedMessage;

    @Value("${congratulations.money.withdrawn}")
    private String moneyWithDrawnMessage;

    public String getWelcomeMessageForUser(){
        val welcomeMessageArr = welcomeMessages.split(",");
        val randomElement = new Random().nextInt(welcomeMessageArr.length);
        return welcomeMessageArr[randomElement];
    }

}
