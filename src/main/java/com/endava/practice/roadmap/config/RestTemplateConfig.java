package com.endava.practice.roadmap.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpRequest;

@Configuration
public class RestTemplateConfig {

    private static final String TOKEN_HEADER_NAME = "X-CMC_PRO_API_KEY";

    @Value("${crypto.portal.token}")
    private String token;

    @Value("${crypto.portal.host}")
    private String host;

    @Profile({"dev", "prod"})
    @Bean
    public RestTemplate coinMarketRestClient (RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors(new HeaderApiTokenInterceptor())
                .build();
    }

    private class HeaderApiTokenInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {

            HttpHeaders headers = request.getHeaders();
            headers.put(TOKEN_HEADER_NAME, Collections.singletonList(token));
            headers.setContentType(MediaType.APPLICATION_JSON);

            return execution.execute(new HostInsertionRequestWrapper(request), body);
        }
    }

    private class HostInsertionRequestWrapper extends HttpRequestWrapper {

        private final HttpRequest request;

        public HostInsertionRequestWrapper(HttpRequest request) {
            super(request);
            this.request=request;
        }

        @Override
        public URI getURI() {
            return fromHttpRequest(request).host(host).build().toUri();
        }
    }
}
