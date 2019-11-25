package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.config.UserServiceConfiguration;
import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.mapper.EntityMapper;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.exceptions.ResourceNotFoundException;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.domain.service.internalservice.CongratulationService;
import com.endava.practice.roadmap.domain.service.internalservice.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.endava.practice.roadmap.util.TestUsers.CLIENT_2_NEW;
import static com.endava.practice.roadmap.util.TestUsers.CLIENT_EXISTING;
import static com.endava.practice.roadmap.util.TestUsers.CLIENT_NON_EXISTING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserServiceConfiguration.class)
@ActiveProfiles("congratulations-test")
class UserServiceTest {

    @MockBean
    private CongratulationService congratulationService;

    @MockBean
    private UserRepository userRepositoryMock;

    @MockBean
    EntityMapper mapperMock;

    @Autowired
    private UserService userSErvice;

    private User client;
    private User clientNew;
    private UserDto clientDto;
    private UserDto clientNewDto;

    @BeforeEach
    void setUp() {
        client = CLIENT_EXISTING.buildUser();
        clientDto = CLIENT_EXISTING.buildUserDto();

        clientNew = CLIENT_2_NEW.buildUser();
        clientNewDto = CLIENT_2_NEW.buildUserDto();
    }

    @AfterEach
    void verifyMockedResources() {
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    void findOne() {

        final String userName = CLIENT_EXISTING.getUsername();

        doReturn(Optional.ofNullable(client)).when(userRepositoryMock).findByUsernameIgnoreCase(userName);
        when(mapperMock.mapData(client)).thenReturn(clientDto);

        assertThat(userSErvice.findOne(userName)).isEqualTo(clientDto);

        verify(userRepositoryMock).findByUsernameIgnoreCase(userName);
        verify(mapperMock).mapData(client);
    }

    @Test
    void findNoUserShouldThrowResourceNotFound() {
        final String nonExistingUserName = CLIENT_NON_EXISTING.getUsername();
        when(userRepositoryMock.findByUsernameIgnoreCase(nonExistingUserName)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userSErvice.findOne(nonExistingUserName)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).findByUsernameIgnoreCase(nonExistingUserName);
    }

    @Test
    void findAll() {
        List<User> userEntityList = Arrays.asList(client, clientNew);
        when(userRepositoryMock.findAll()).thenReturn(userEntityList);
        when(mapperMock.mapData(any(User.class))).thenReturn(clientDto, clientNewDto);

        assertThat(userSErvice.findAll()).containsOnly(clientDto, clientNewDto);

        verify(userRepositoryMock).findAll();
        verify(mapperMock, times(2)).mapData(any(User.class));
    }

    @Test
    void create() {
        when(userRepositoryMock.save(client)).thenReturn(client);
        lenient().when(mapperMock.mapData(clientDto)).thenReturn(client);
        lenient().when(mapperMock.mapData(client)).thenReturn(clientDto);

        assertThat(userSErvice.create(clientDto)).isEqualTo(clientDto);

        verify(userRepositoryMock).save(client);
        verify(mapperMock).mapData(clientDto);
        verify(mapperMock).mapData(client);

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

        final UserDto userResult = userSErvice.replace(userName, clientNewDto);
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

        assertThatThrownBy(() -> userSErvice.replace(nonExistingUserName, clientNewDto)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).findByUsernameIgnoreCase(nonExistingUserName);
    }


    @Test
    void delete() {
        final String clientUserName = CLIENT_2_NEW.getUsername();
        when(userRepositoryMock.existsByUsernameIgnoreCase(clientUserName)).thenReturn(true);
        userSErvice.delete(clientUserName);
        verify(userRepositoryMock).deleteUserByUsernameIgnoreCase(clientUserName);
        verify(userRepositoryMock).existsByUsernameIgnoreCase(clientUserName);
    }

    @Test
    void deleteMissingUserShouldThrowResourceNotFound() {
        final String clientUserName = CLIENT_NON_EXISTING.getUsername();
        when(userRepositoryMock.existsByUsernameIgnoreCase(clientUserName)).thenReturn(false);
        assertThatThrownBy(() -> userSErvice.delete(clientUserName)).isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryMock).existsByUsernameIgnoreCase(clientUserName);
    }

    @Test
    public void test(){
        IntStream pages = IntStream.of(200, 300);
        IntSummaryStatistics intSummaryStatistics = pages.summaryStatistics();
        System.out.println("total: " + intSummaryStatistics.getSum() + "count: " + intSummaryStatistics.getCount() );
    }
}