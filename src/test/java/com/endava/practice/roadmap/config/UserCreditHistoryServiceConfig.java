package com.endava.practice.roadmap.config;

import com.endava.practice.roadmap.domain.service.internalservice.CongratulationService;
import com.endava.practice.roadmap.domain.service.internalservice.CreditHistoryService;
import com.endava.practice.roadmap.domain.service.internalservice.UserCreditHistoryService;
import com.endava.practice.roadmap.domain.service.internalservice.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
//@ComponentScan("com.endava.practice.roadmap.domain.service")
public class UserCreditHistoryServiceConfig {

    @Bean
    public UserCreditHistoryService userCeditHistoryService(
        UserService userSErvice,
        CreditHistoryService creditHistoryService,
        CongratulationService congratulationSErvice) {
        return new UserCreditHistoryService(userSErvice, creditHistoryService, congratulationSErvice);
    }

    @PostConstruct
    public void init(){
        System.out.println("666 ==============================");
    }
}
