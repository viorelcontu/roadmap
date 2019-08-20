package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.mapper.EntityMapper;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.exceptions.ResourceNotFoundException;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.endava.practice.roadmap.util.TestUsers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    EntityMapper mapperMock;

    @InjectMocks
    private UserService userService;

    private User client;
    private User clientNew;
    private UserDto clientDto;
    private UserDto clientNewDto;

    @BeforeEach
    void setUp () {
        client = CLIENT_EXISTING.buildUser();
        clientDto = CLIENT_EXISTING.buildUserDto();

        clientNew = CLIENT_2_NEW.buildUser();
        clientNewDto = CLIENT_2_NEW.buildUserDto();
    }

    @AfterEach
    void verifyMockedResources () {
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    void findOne() {

        final String userName = CLIENT_EXISTING.getUsername();

        when(userRepositoryMock.findByUsernameIgnoreCase(userName)).thenReturn(Optional.ofNullable(client));
        when(mapperMock.mapData(client)).thenReturn(clientDto);

        assertThat(userService.findOne(userName)).isEqualTo(clientDto);

        verify(userRepositoryMock).findByUsernameIgnoreCase(userName);
        verify(mapperMock).mapData(client);

    }

    @Test
    void findNoUserShouldThrowResourceNotFound () {
        final String nonExistingUserName = CLIENT_NON_EXISTING.getUsername();
        when(userRepositoryMock.findByUsernameIgnoreCase(nonExistingUserName)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.findOne(nonExistingUserName)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).findByUsernameIgnoreCase(nonExistingUserName);
    }

    @Test
    void findAll() {
        List<User> userEntityList = Arrays.asList(client, clientNew);
        when(userRepositoryMock.findAll()).thenReturn(userEntityList);
        when(mapperMock.mapData(any(User.class))).thenReturn(clientDto, clientNewDto);

        assertThat(userService.findAll()).containsOnly(clientDto, clientNewDto);

        verify(userRepositoryMock).findAll();
        verify(mapperMock, times(2)).mapData(any(User.class));
    }

    @Test
    void create() {
        when (userRepositoryMock.save(client)).thenReturn(client);
        lenient().when (mapperMock.mapData(clientDto)).thenReturn(client);
        lenient().when (mapperMock.mapData(client)).thenReturn(clientDto);

        assertThat(userService.create(clientDto)).isEqualTo(clientDto);

        verify(userRepositoryMock).save(client);
        verify (mapperMock).mapData(clientDto);
        verify (mapperMock).mapData(client);

    }

    @Test
    void replace() {
        final String userName = CLIENT_EXISTING.getUsername();

        when(userRepositoryMock.findByUsernameIgnoreCase(userName)).thenReturn(Optional.of(client));

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            UserDto dto = (UserDto) args[0];
            User entity = (User) args[1];
            entity.setUsername(dto.getUsername());
            entity.setNickname(dto.getNickname());
            entity.setEmail(dto.getEmail());
            entity.setCredits(dto.getCredits());
            entity.setActive(dto.getActive());
            return null;
        }).when(mapperMock).updateUser(clientNewDto, client);

        when(userRepositoryMock.save(client)).thenReturn(client);
        when(mapperMock.mapData(client)).thenReturn(clientNewDto);

        final UserDto userResult = userService.replace(userName, clientNewDto);
        assertThat(userResult).isEqualToComparingFieldByField(CLIENT_2_NEW.buildUserDto());

        verify(userRepositoryMock).findByUsernameIgnoreCase(userName);
        verify(mapperMock).updateUser(eq(clientNewDto), ArgumentMatchers.any());
        verify(mapperMock).mapData(client);
        verify(userRepositoryMock).save(client);
    }

    @Test
    void replaceNonExistingShouldThrowResourceNotFound() {
        final String nonExistingUserName = CLIENT_NON_EXISTING.getUsername();

        when(userRepositoryMock.findByUsernameIgnoreCase(nonExistingUserName)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.replace(nonExistingUserName, clientNewDto)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).findByUsernameIgnoreCase(nonExistingUserName);
    }


    @Test
    void delete() {
        final String clientUserName = CLIENT_2_NEW.getUsername();
        when (userRepositoryMock.existsByUsernameIgnoreCase(clientUserName)).thenReturn(true);
        userService.delete(clientUserName);
        verify(userRepositoryMock).deleteUserByUsernameIgnoreCase(clientUserName);
        verify(userRepositoryMock).existsByUsernameIgnoreCase(clientUserName);
    }

    @Test
    void deleteMissingUserShouldThrowResourceNotFound() {
        final String clientUserName = CLIENT_NON_EXISTING.getUsername();
        when (userRepositoryMock.existsByUsernameIgnoreCase(clientUserName)).thenReturn(false);
        assertThatThrownBy(() -> userService.delete(clientUserName)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).existsByUsernameIgnoreCase(clientUserName);
    }
}