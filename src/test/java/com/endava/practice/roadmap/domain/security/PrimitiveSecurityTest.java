package com.endava.practice.roadmap.domain.security;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.enums.Permission;
import com.endava.practice.roadmap.domain.model.exceptions.AuthenticationException;
import com.endava.practice.roadmap.domain.model.exceptions.ForbiddenException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.endava.practice.roadmap.domain.model.enums.Permission.CLIENT_ADMIN;
import static com.endava.practice.roadmap.domain.model.enums.Permission.OPERATOR_ADMIN;
import static com.endava.practice.roadmap.util.TestUsers.ADMIN_EXISTING;
import static com.endava.practice.roadmap.util.TestUsers.CLIENT_INACTIVE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrimitiveSecurityTest {

    @Mock
    UserRepository userDaoMock;

    @InjectMocks
    PrimitiveSecurity primitiveSecurity;

    @AfterEach
    void tearDown() {
        ReflectionTestUtils.setField(primitiveSecurity, "currentUserPermissions", new ThreadLocal<Permission>());
        verifyNoMoreInteractions(userDaoMock);
    }

    @Test
    void successfulAuthentication() {
        final User authenticatedUser = ADMIN_EXISTING.buildUser();
        authenticatedUser.getGroup().setPermissions(EnumSet.of(OPERATOR_ADMIN, CLIENT_ADMIN));
        final UUID token = authenticatedUser.getToken();

        when(userDaoMock.findByTokenEquals(token)).thenReturn(Optional.of(authenticatedUser));

        primitiveSecurity.authenticateUser(token.toString());

        Assertions.assertThat(primitiveSecurity.getCurrentUser())
                .isEqualToComparingFieldByField(authenticatedUser);

        verify(userDaoMock).findByTokenEquals(token);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ",
            "5zzzzzzf-4672-4824-807d-087730c7ec62",
            "5f0909af46724824807d087730c7ec62"})
    void invalidTokenThrowsAuthenticationFailed(String input) {
        assertThatThrownBy(() -> primitiveSecurity.authenticateUser(input)).
                isInstanceOf(AuthenticationException.class);
    }

    @Test
    void nullTokenThrowsAuthenticationFailed() {
        assertThatThrownBy(() -> primitiveSecurity.authenticateUser(null)).
                isInstanceOf(AuthenticationException.class);
    }

    @Test
    void inactiveUserTokenThrowsAuthenticationFailed() {
        final User inactiveUser = CLIENT_INACTIVE.buildUser();
        final UUID token = inactiveUser.getToken();
        when(userDaoMock.findByTokenEquals(token)).thenReturn(Optional.of(inactiveUser));

        assertThatThrownBy(() -> primitiveSecurity.authenticateUser(token.toString()))
                .isInstanceOf(AuthenticationException.class);

        assertThatThrownBy (() -> primitiveSecurity.getCurrentUser())
                .isInstanceOf(AuthenticationException.class);

        verify(userDaoMock).findByTokenEquals(token);
    }

    @Test
    void authorizeActionAccessGranted() {
        final Object object = ReflectionTestUtils.getField(primitiveSecurity, "currentUserPermissions");
        final ThreadLocal<Set<Permission>> currentUserPermissions = (ThreadLocal<Set<Permission>>) object;

        currentUserPermissions.set(EnumSet.of(CLIENT_ADMIN));

        assertThatCode( () -> primitiveSecurity.authorizeAction(CLIENT_ADMIN))
                .doesNotThrowAnyException();
    }

    @Test
    void authorizeActionAccessDenied() {
        final Object object = ReflectionTestUtils.getField(primitiveSecurity, "currentUserPermissions");
        final ThreadLocal<Set<Permission>> currentUserPermissions = (ThreadLocal<Set<Permission>>) object;

        currentUserPermissions.set(EnumSet.of(CLIENT_ADMIN));

        assertThatThrownBy( () -> primitiveSecurity.authorizeAction(OPERATOR_ADMIN))
                .isInstanceOf(ForbiddenException.class);
    }
}