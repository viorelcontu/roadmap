package com.endava.practice.roadmap.service;

import com.endava.practice.roadmap.persistence.dao.UserRepository;
import com.endava.practice.roadmap.persistence.entity.User;
import com.endava.practice.roadmap.web.exception.BadRequestException;
import com.endava.practice.roadmap.web.exception.ResourceNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void findNoUserShouldThrowResourceNotFound () {
        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.findOne(USER_ID)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).findById(USER_ID);
    }

    @Test
    void findAll() {
        final User anotherUser = new User (USER2_ID, USER2_NAME);
        List<User> userList = Arrays.asList(user, anotherUser);
        when(userRepositoryMock.findAll()).thenReturn(userList);

        assertThat(userService.findAll()).containsOnly(user, anotherUser);

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
        when (userRepositoryMock.existsById(USER_ID)).thenReturn(true);

        userService.update(USER_ID,user);

        verify(userRepositoryMock).save(user);
        verify(userRepositoryMock).existsById(USER_ID);
    }

    @Test
    void updateNonExistingShouldThrowResourceNotFound() {
        when (userRepositoryMock.existsById(USER_ID)).thenReturn(false);
        assertThatThrownBy(() -> userService.update(USER_ID,user)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).existsById(USER_ID);
    }

    @Test
    void updateMismatchedIdsShouldThrowBadRequest() {
        assertThatThrownBy(() -> userService.update(USER2_ID, user)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void delete() {
        when (userRepositoryMock.existsById(USER_ID)).thenReturn(true);
        userService.delete(USER_ID);
        verify(userRepositoryMock).deleteById(USER_ID);
        verify(userRepositoryMock).existsById(USER_ID);
    }

    @Test
    void deleteMissingUserShouldThrowResourceNotFound() {
        when (userRepositoryMock.existsById(USER_ID)).thenReturn(false);
        assertThatThrownBy(() -> userService.delete(USER_ID)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).existsById(USER_ID);
    }

    @Test
    void count() {
        final long USER_COUNT = 99L;
        when (userRepositoryMock.count()).thenReturn(USER_COUNT);
        assertThat(userService.count()).isEqualTo(USER_COUNT);
        verify(userRepositoryMock).count();
    }
}