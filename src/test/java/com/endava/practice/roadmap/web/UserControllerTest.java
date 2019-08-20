package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.util.TestUsers;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectWriter objectWriter;

    private UserDto dto;
    private String userName;

    @BeforeEach
    void setUp() {
        dto = TestUsers.CLIENT_EXISTING.buildUserDto();
        userName = dto.getUsername();
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

        when (userServiceMock.replace(dto.getUsername(), dto)).thenReturn(dto);

        mockMvc.perform(
            put("/users/{username}", userName)
                .contentType(JSON_UTF8)
                .content(requestJson))
            .andDo(print())
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.username", equalTo(dto.getUsername())));;

        verify(userServiceMock).replace(userName, dto);
    }

    @Test
    void shouldDeleteExistingUser() throws Exception {
        mockMvc.perform(delete("/users/{username}", userName))
            .andDo(print())
            .andExpect(status().isNoContent());

        verify(userServiceMock).delete(userName);
    }
}