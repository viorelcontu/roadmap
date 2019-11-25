package com.endava.practice.roadmap.config;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.mapper.EntityMapper;
import com.endava.practice.roadmap.domain.service.internalservice.UserService;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

//@Configuration
@TestConfiguration
public class UserServiceConfiguration {

    @Bean
    public UserService userSErvice(
        UserRepository userRepository,
        EntityMapper entityMapper){
        return new UserService(userRepository, entityMapper);
    }
}
