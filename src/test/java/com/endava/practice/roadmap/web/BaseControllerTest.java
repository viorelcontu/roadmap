package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.config.TestConfig;
import com.endava.practice.roadmap.web.interceptors.AuthenticationInterceptor;
import com.endava.practice.roadmap.domain.service.UserService;
import com.endava.practice.roadmap.domain.service.coinmarket.MarketService;
import com.endava.practice.roadmap.web.interceptors.LoggingInterceptor;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest
@Import(TestConfig.class)
abstract public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    public static final MediaType JSON_UTF8 = new MediaType (
            APPLICATION_JSON.getType(),
            APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
        );

    @MockBean
    UserService userServiceMock;

    @MockBean
    MarketService marketService;

    @MockBean
    AuthenticationInterceptor authenticationInterceptor;

    @MockBean
    LoggingInterceptor loggingInterceptor;

    @AfterEach
    void verifyMockedResources () {
        verifyNoMoreInteractions(userServiceMock);
    }
}
