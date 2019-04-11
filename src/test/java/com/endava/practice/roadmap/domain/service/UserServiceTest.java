package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.exception.BadRequestException;
import com.endava.practice.roadmap.domain.exception.ResourceNotFoundException;
import com.endava.practice.roadmap.domain.model.dto.UserDto;
import com.endava.practice.roadmap.domain.model.entity.User;
import com.endava.practice.roadmap.domain.model.mapper.UserMapper;
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

import static com.endava.practice.roadmap.util.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    UserMapper mapperMock;

    @InjectMocks
    private UserService userService;

    private User userEntity;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userEntity = new User(USER_ID, USER_NAME);
        userDto = new UserDto(USER_ID, USER_NAME);
    }

    @AfterEach
    void verifyMockedResources () {
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    void findOne() {
        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.ofNullable(userEntity));
        when(mapperMock.toDto(userEntity)).thenReturn(userDto);

        assertThat(userService.findOne(USER_ID)).isEqualTo(userDto);

        verify(userRepositoryMock).findById(USER_ID);
        verify(mapperMock).toDto(userEntity);

    }

    @Test
    void findNoUserShouldThrowResourceNotFound () {
        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.findOne(USER_ID)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).findById(USER_ID);
    }

    @Test
    void findAll() {
        final User userEntity2 = new User(USER2_ID, USER2_NAME);
        final UserDto userDto2 = new UserDto(USER2_ID, USER2_NAME);

        List<User> userEntityList = Arrays.asList(userEntity, userEntity2);
        when(userRepositoryMock.findAll()).thenReturn(userEntityList);
        when(mapperMock.toDto(any(User.class))).thenReturn(userDto, userDto2);

        assertThat(userService.findAll()).containsOnly(userDto, userDto2);

        verify(userRepositoryMock).findAll();
        verify(mapperMock, times(2)).toDto(any(User.class));
    }

    @Test
    void create() {
        when (userRepositoryMock.save(userEntity)).thenReturn(userEntity);
        lenient().when (mapperMock.toEntity(userDto)).thenReturn(userEntity);
        lenient().when (mapperMock.toDto(userEntity)).thenReturn(userDto);

        assertThat(userService.create(userDto)).isEqualTo(userDto);

        verify(userRepositoryMock).save(userEntity);
        verify (mapperMock).toEntity(userDto);
        verify (mapperMock).toDto(userEntity);

    }

    @Test
    void update() {
        when (userRepositoryMock.save(userEntity)).thenReturn(userEntity);
        when (userRepositoryMock.existsById(USER_ID)).thenReturn(true);
        when (mapperMock.toEntity(userDto)).thenReturn(userEntity);

        userService.update(USER_ID, userDto);

        verify(userRepositoryMock).save(userEntity);
        verify(userRepositoryMock).existsById(USER_ID);
        verify(mapperMock).toEntity(userDto);
    }

    @Test
    void updateNonExistingShouldThrowResourceNotFound() {
        when (userRepositoryMock.existsById(USER_ID)).thenReturn(false);
        assertThatThrownBy(() -> userService.update(USER_ID, userDto)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).existsById(USER_ID);
    }

    @Test
    void updateMismatchedIdsShouldThrowBadRequest() {
        assertThatThrownBy(() -> userService.update(USER2_ID, userDto)).isInstanceOf(BadRequestException.class);
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