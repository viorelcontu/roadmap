package com.endava.practice.roadmap.web.controller;

import com.endava.practice.roadmap.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.mockito.Mockito.verifyNoMoreInteractions;

//@ExtendWith(SpringExtension.class)
@WebMvcTest
abstract public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    public static final MediaType JSON_UTF8 = new MediaType (
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
        );

    @MockBean
    UserService userServiceMock;

    @AfterEach
    void verifyMockedResources () {
        verifyNoMoreInteractions(userServiceMock);
    }

}
