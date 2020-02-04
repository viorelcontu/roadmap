package com.endava.practice.roadmap.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@TestConfiguration
public class TestConfig {

    @Bean ObjectWriter objectWriter() {
        return new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Bean
    RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(new CustomRestTemplateCustomizer());
    }

    private static class CustomRestTemplateCustomizer implements RestTemplateCustomizer {
        @Override
        public void customize(RestTemplate restTemplate) {
            restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
        }
    }

    @Slf4j
    private static class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(
                HttpRequest request, byte[] body,
                ClientHttpRequestExecution execution) throws IOException {

            logRequestDetails(request);
            return execution.execute(request, body);
        }
        private void logRequestDetails(HttpRequest request) {
            log.debug("REST call: Headers = {}", request.getHeaders());
        }
    }
}
