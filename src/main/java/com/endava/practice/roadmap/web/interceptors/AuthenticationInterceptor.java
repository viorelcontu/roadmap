package com.endava.practice.roadmap.web.interceptors;

import com.endava.practice.roadmap.domain.security.SecurityProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
@Profile("!no-security")
public class AuthenticationInterceptor implements HandlerInterceptor {

    public static final String AUTHENTICATION_HEADER = "X-API-KEY";

    private final SecurityProvider securityProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userToken = request.getHeader(AUTHENTICATION_HEADER);
        securityProvider.authenticateUser(userToken);
        return true;
    }
}
