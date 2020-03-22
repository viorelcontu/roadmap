package com.endava.practice.roadmap.config;

import com.endava.practice.roadmap.web.interceptors.AuthenticationInterceptor;
import com.endava.practice.roadmap.web.interceptors.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Configuration
public class SecurityInterceptorConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;

    @Setter(onMethod_ = {@Autowired(required = false)})
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final boolean securityActivated = nonNull(authenticationInterceptor);
        if (securityActivated)
            registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**");

        registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");
    }
}
