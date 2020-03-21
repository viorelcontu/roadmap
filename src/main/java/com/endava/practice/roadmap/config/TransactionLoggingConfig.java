package com.endava.practice.roadmap.config;

import com.endava.practice.roadmap.domain.logging.LoggingJpaTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionLoggingConfig {

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new LoggingJpaTransactionManager();
    }
}
