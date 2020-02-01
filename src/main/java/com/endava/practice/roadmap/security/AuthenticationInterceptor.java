package com.endava.practice.roadmap.security;

import com.endava.practice.roadmap.config.filters.WrappingFilter;
import com.endava.practice.roadmap.domain.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("security")
public class AuthenticationInterceptor implements HandlerInterceptor {

    public static final String AUTHENTICATION_HEADER = "X-API-KEY";

    private final SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userToken = request.getHeader(AUTHENTICATION_HEADER);
        securityService.authenticateUser(userToken);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestData = WrappingFilter.getRequestData(request);
        String responseData = WrappingFilter.getResponseData(response);
        log.debug("Request data:\n {}", requestData);
        log.debug("Response data:\n {}", responseData);
    }
}
