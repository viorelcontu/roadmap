package com.endava.practice.roadmap.service;

import com.endava.practice.roadmap.persistence.dao.UserRepository;
import com.endava.practice.roadmap.persistence.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.endava.practice.roadmap.TestUtils.USER2_ID;
import static com.endava.practice.roadmap.TestUtils.USER2_NAME;
import static com.endava.practice.roadmap.TestUtils.USER_ID;
import static com.endava.practice.roadmap.TestUtils.USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(USER_ID, USER_NAME);
    }

    @AfterEach
    void verifyMockedResources () {
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    void findOne() {
        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.ofNullable(user));
        assertThat(userService.findOne(USER_ID)).isEqualTo(user);
        verify(userRepositoryMock).findById(USER_ID);
    }

    @Test
    void findAll() {
        User anotherUser = new User (USER2_ID, USER2_NAME);
        List<User> userList = Arrays.asList(user, anotherUser);

        when(userRepositoryMock.findAll()).thenReturn(userList);
        assertThat(userService.findAll()).isEqualTo(userList);
        verify(userRepositoryMock).findAll();
    }

    @Test
    void create() {
        when (userRepositoryMock.save(user)).thenReturn(user);
        assertThat(userService.create(user)).isEqualTo(user);
        verify(userRepositoryMock).save(user);
    }

    @Test
    void update() {
        when (userRepositoryMock.save(user)).thenReturn(user);
        userService.update(USER_ID,user);
        verify(userRepositoryMock).save(user);
    }

    @Test
    void delete() {
        userService.delete(USER_ID);
        verify(userRepositoryMock).deleteById(USER_ID);
    }

    @Test
    void count() {
        when (userRepositoryMock.count()).thenReturn(99L);
        assertThat(userService.count()).isEqualTo(99L);
        verify(userRepositoryMock).count();
    }
}