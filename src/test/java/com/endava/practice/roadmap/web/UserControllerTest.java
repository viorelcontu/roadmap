package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.util.TestUsers;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectWriter objectWriter;

    private UserDto dto;
    private String userName;

    @BeforeEach
    void setUp() throws Exception {
        dto = TestUsers.CLIENT_EXISTING.buildUserDto();
        userName = dto.getUsername();

        when (authenticationInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        when (loggingInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        doNothing().when(authenticationInterceptor).postHandle(any(), any(), any(), any());
    }

    @Test
    void getAllUsers200() throws Exception {
        TestUsers user1 = TestUsers.MANAGER_EXISTING;
        TestUsers user2 = TestUsers.CLIENT_EXISTING;

        when(userServiceMock.findAll()).thenReturn(asList(user1.buildUserDto(), user2.buildUserDto()));

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].username", containsInAnyOrder(user1.getUsername(), user2.getUsername())))
                .andExpect(jsonPath("$[*].email", containsInAnyOrder(user1.getEmail(), user2.getEmail())));

        verify(userServiceMock).findAll();
    }

    @Test
    void getByUserName200() throws Exception {
        when(userServiceMock.findOne(userName)).thenReturn(dto);

        mockMvc.perform(get("/users/{username}", userName))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(JSON_UTF8))
            .andExpect(jsonPath("$.username").value(dto.getUsername()));

        verify(userServiceMock).findOne(userName);
    }

    @Test
    void postNewUser201() throws Exception {
        when(userServiceMock.create(dto)).thenReturn(dto);

        final String requestJson = objectWriter.writeValueAsString(dto);

        mockMvc.perform(
            post("/users")
                .contentType(JSON_UTF8)
                .content(requestJson))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(JSON_UTF8))
            .andExpect(jsonPath("$.username", equalTo(dto.getUsername())));

        verify(userServiceMock).create(dto);
    }

    @Test
    void putUser202() throws Exception {
        final String requestJson = objectWriter.writeValueAsString(dto);

        when (userServiceMock.replace(dto, dto.getUsername())).thenReturn(dto);

        mockMvc.perform(
            put("/users/{username}", userName)
                .contentType(JSON_UTF8)
                .content(requestJson))
            .andDo(print())
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.username", equalTo(dto.getUsername())));;

        verify(userServiceMock).replace(dto, userName);
    }

    @Test
    void shouldDeleteExistingUser() throws Exception {
        mockMvc.perform(delete("/users/{username}", userName))
            .andDo(print())
            .andExpect(status().isNoContent());

        verify(userServiceMock).delete(userName);
    }
}