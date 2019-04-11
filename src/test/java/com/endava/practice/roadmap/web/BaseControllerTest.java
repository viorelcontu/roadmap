package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.config.TestConfig;
import com.endava.practice.roadmap.domain.service.AbstractCrudService;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.mockito.Mockito.verifyNoMoreInteractions;

@WebMvcTest
@Import(TestConfig.class)
abstract public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    public static final MediaType JSON_UTF8 = new MediaType (
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
        );

    @MockBean
    AbstractCrudService userServiceMock;

    @AfterEach
    void verifyMockedResources () {
        verifyNoMoreInteractions(userServiceMock);
    }

}
