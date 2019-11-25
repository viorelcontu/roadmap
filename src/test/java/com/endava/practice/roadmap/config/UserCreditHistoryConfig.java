package com.endava.practice.roadmap.config;

import com.endava.practice.roadmap.domain.dao.CreditHistoryRepository;
import com.endava.practice.roadmap.domain.mapper.EntityMapperImpl;
import com.endava.practice.roadmap.domain.service.internalservice.CongratulationService;
import com.endava.practice.roadmap.domain.service.internalservice.CreditHistoryService;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@EnableJpaRepositories("com.endava.practice.roadmap.domain.dao")
@EnableAutoConfiguration
@EntityScan("com.endava.practice.roadmap.domain.model.entities")
@Import({UserCreditHistoryServiceConfig.class, UserServiceConfiguration.class,EntityMapperImpl.class})
//@Import(EntityMapperImpl.class)
public class UserCreditHistoryConfig {

    @Bean
    public CongratulationService congratulationSErvice(){
        return new CongratulationService();
    }

    @Bean
    public CreditHistoryService creditHistoryService(CreditHistoryRepository creditHistoryRepository){
        return new CreditHistoryService(creditHistoryRepository);
    }
}