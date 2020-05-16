package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.util.TestUsers;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectWriter objectWriter;

    private UserDto dto;
    private String username;

    @BeforeEach
    void setUp() throws Exception {
        dto = TestUsers.CLIENT_EXISTING.buildUserDto();
        username = dto.getUsername();

        when (authenticationInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        when (loggingInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        doNothing().when(authenticationInterceptor).postHandle(any(), any(), any(), any());
    }

    @Test
    void getAllUsers200() throws Exception {
        TestUsers user1 = TestUsers.MANAGER_EXISTING;
        TestUsers user2 = TestUsers.CLIENT_EXISTING;

        when(userControllerMock.findAll()).thenReturn(asList(user1.buildUserDto(), user2.buildUserDto()));

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].username", containsInAnyOrder(user1.getUsername(), user2.getUsername())))
                .andExpect(jsonPath("$[*].email", containsInAnyOrder(user1.getEmail(), user2.getEmail())));

        verify(userControllerMock).findAll();
    }

    @Test
    void getByUserName200() throws Exception {
        when(userControllerMock.find(username)).thenReturn(dto);

        mockMvc.perform(get("/users/{username}", username))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.username").value(dto.getUsername()));

        verify(userControllerMock).find(username);
    }

    @Test
    void postNewUser201() throws Exception {
        when(userControllerMock.create(dto)).thenReturn(dto);

        final String requestJson = objectWriter.writeValueAsString(dto);

        mockMvc.perform(
            post("/users")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.username", equalTo(dto.getUsername())));

        verify(userControllerMock).create(dto);
    }

    @Test
    void putUser202() throws Exception {
        final String requestJson = objectWriter.writeValueAsString(dto);

        when (userControllerMock.amend(dto.getUsername(), dto)).thenReturn(dto);

        mockMvc.perform(
            put("/users/{username}", username)
                .contentType(APPLICATION_JSON
                )
                .content(requestJson))
            .andDo(print())
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.username", equalTo(dto.getUsername())));;

        verify(userControllerMock).amend(username, dto);
    }

    @Test
    void shouldDeleteExistingUser() throws Exception {
        mockMvc.perform(delete("/users/{username}", username))
            .andDo(print())
            .andExpect(status().isNoContent());

        verify(userControllerMock).delete(username);
    }
}