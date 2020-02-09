package com.endava.practice.roadmap.web.interceptors;

import com.endava.practice.roadmap.web.filters.WrappingFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Inbound Request Method: {}; URI: {}",request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestData = WrappingFilter.getRequestData(request);
        String responseData = WrappingFilter.getResponseData(response);
        log.debug("Request Data:\n {}", requestData);
        log.debug("Response data:\n {}", responseData);
    }
}
