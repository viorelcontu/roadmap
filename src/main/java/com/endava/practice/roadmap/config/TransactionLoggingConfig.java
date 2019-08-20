package com.endava.practice.roadmap.config;

import com.endava.practice.roadmap.domain.logging.LoggingJpaTransactionManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("dev")
public class TransactionLoggingConfig {

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new LoggingJpaTransactionManager();
    }
}
