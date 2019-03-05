package com.endava.practice.roadmap.configuration;

import com.endava.practice.roadmap.persistence.dao.UserRepository;
import com.endava.practice.roadmap.persistence.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {
    private final static String PRELOADING = "Preloading users {}";

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            List<User> userList = Arrays.asList(new User(1L, "John Doe"),
                new User(2L, "Bill Smith"));
            log.info(PRELOADING, repository.saveAll(userList));
        };
    }
}
