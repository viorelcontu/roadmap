package com.endava.practice.roadmap.config;

import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.util.Collections;

import static com.endava.practice.roadmap.domain.model.exceptions.LocalInternalServerError.ofUnspecified;
import static com.endava.practice.roadmap.domain.model.exceptions.LocalInternalServerError.ofWrongMarketToken;

@Configuration
@Profile({"dev", "prod"})
public class RestTemplateConfig {

    private static final String TOKEN_HEADER_NAME = "X-CMC_PRO_API_KEY";

    @Value("${crypto.portal.token}")
    private String token;

    @Value("${crypto.portal.host}")
    private String host;

    @Bean
    public RestTemplate coinMarketRestClient (RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors(new HeaderApiTokenInterceptor())
                .uriTemplateHandler(new DefaultUriBuilderFactory(host))
                .errorHandler(new ErrorResponseHandler())
                .build();
    }

    @Slf4j
    public static class ErrorResponseHandler extends DefaultResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            log.debug("CoinMarketCap Exception, code {}, status {}", response.getStatusCode(), response.getStatusText());

            switch (response.getStatusCode()) {
                case UNAUTHORIZED:
                    throw ofWrongMarketToken();
                case BAD_REQUEST:
                case FORBIDDEN:
                case TOO_MANY_REQUESTS:
                case INTERNAL_SERVER_ERROR:
                default:
                    throw ofUnspecified();
            }
        }
    }

    private class HeaderApiTokenInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {

            HttpHeaders headers = request.getHeaders();
            headers.put(TOKEN_HEADER_NAME, Collections.singletonList(token));
            headers.setContentType(MediaType.APPLICATION_JSON);

            return execution.execute(request, body);
        }
    }
}
