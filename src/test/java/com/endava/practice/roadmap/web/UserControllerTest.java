package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.endava.practice.roadmap.util.TestUtils.*;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectWriter objectWriter;

    private UserDto user;

    @BeforeEach
    void setUp() {
        user = new UserDto(USER_ID, USER_NAME);
    }

    @Test
    void shouldReturnExistingUserById() throws Exception {
        when(userServiceMock.findOne(USER_ID)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", USER_ID))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(JSON_UTF8))
            .andExpect(jsonPath("$.id").value(USER_ID))
            .andExpect(jsonPath("$.name").value(USER_NAME));

        verify(userServiceMock).findOne(USER_ID);
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        UserDto anotherUser = new UserDto(USER2_ID, USER2_NAME);

        when(userServiceMock.findAll()).thenReturn(asList(user, anotherUser));

        mockMvc.perform(get("/users"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(USER_ID))
            .andExpect(jsonPath("$[0].name").value(USER_NAME))
            .andExpect(jsonPath("$[1].id").value(USER2_ID))
            .andExpect(jsonPath("$[1].name").value(USER2_NAME));

        verify(userServiceMock).findAll();
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        when(userServiceMock.create(user)).thenReturn(user);

        String requestJson = objectWriter.writeValueAsString(user);

        mockMvc.perform(
            post("/users")
                .contentType(JSON_UTF8)
                .content(requestJson))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(JSON_UTF8))
            .andExpect(jsonPath("$.id").value(USER_ID))
            .andExpect(jsonPath("$.name").value(USER_NAME));

        verify(userServiceMock).create(user);
    }

    @Test
    void shouldUpdateExistingUserById() throws Exception {
        String requestJson = objectWriter.writeValueAsString(user);

        mockMvc.perform(
            put("/users/{id}", USER_ID)
                .contentType(JSON_UTF8)
                .content(requestJson))
            .andDo(print())
            .andExpect(status().isAccepted());

        verify(userServiceMock).update(USER_ID, user);
    }

    @Test
    void shouldDeleteExistingUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", USER_ID))
            .andDo(print())
            .andExpect(status().isNoContent());

        verify(userServiceMock).delete(USER_ID);
    }

    @Test
    void shouldReturnUserCount() throws Exception {
        when(userServiceMock.count()).thenReturn(99L);

        mockMvc.perform(get("/users/count"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("99"));

        verify(userServiceMock).count();
    }
}