package com.endava.practice.roadmap.domain.security;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.model.annotations.RequirePermission;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.enums.Permission;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.util.TestUsers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static com.endava.practice.roadmap.domain.model.enums.Permission.CLIENT_ADMIN;
import static com.endava.practice.roadmap.domain.model.enums.Permission.OPERATOR_ADMIN;
import static com.endava.practice.roadmap.util.TestUsers.ADMIN_EXISTING;
import static com.endava.practice.roadmap.util.TestUsers.AUDIT_EXISTING;
import static com.endava.practice.roadmap.util.TestUsers.CLIENT_2_NEW;
import static com.endava.practice.roadmap.util.TestUsers.CLIENT_EXISTING;
import static com.endava.practice.roadmap.util.TestUsers.CLIENT_NEW;
import static com.endava.practice.roadmap.util.TestUsers.MANAGER_EXISTING;
import static com.endava.practice.roadmap.util.TestUtils.requiredPermissionFactory;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationManagerTest {

    @Mock
    private SecurityProvider securityService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationManager authorizationManager;

    @Captor
    private ArgumentCaptor<Permission> captor;

    @Test
    void validatePermissionsOnMethods() {
        final RequirePermission requirePermission = requiredPermissionFactory(CLIENT_ADMIN);
        authorizationManager.validatePermissionsOnMethods(requirePermission);
        validateSecurityServiceCall(CLIENT_ADMIN);
    }

    @Test
    void validatePermissionsOnTypes() {
        final RequirePermission requirePermission = requiredPermissionFactory(CLIENT_ADMIN);
        authorizationManager.validatePermissionsOnTypes(requirePermission);
        validateSecurityServiceCall(CLIENT_ADMIN);
    }

    @Test
    void validateNewUserClient() {
        final UserDto userDto = CLIENT_NEW.buildUserDto();
        authorizationManager.validateNewUser(userDto);
        validateSecurityServiceCall(CLIENT_ADMIN);
    }

    @Test
    void validateNewUserOperator() {
        final UserDto userDto = MANAGER_EXISTING.buildUserDto();
        authorizationManager.validateNewUser(userDto);
        validateSecurityServiceCall(OPERATOR_ADMIN);
    }

    @Test
    void validateUserUpdateWithNoRoleChange() {
        final User originalUser = CLIENT_NEW.buildUser();
        final String username = originalUser.getUsername();
        when(userRepository.findByUsernameIgnoreCase(username))
                .thenReturn(Optional.of(originalUser));

        final UserDto updatedUserDto = CLIENT_2_NEW.buildUserDto();
        authorizationManager.validateUserUpdate(updatedUserDto, username);

        validateSecurityServiceCall(CLIENT_ADMIN);
    }

    @ParameterizedTest
    @MethodSource("roleChangeArguments")
    void validateUserUpdateWithRoleChange(TestUsers testUser, TestUsers updatedTestUser) {
        final User user = testUser.buildUser();
        final String username = user.getUsername();
        when(userRepository.findByUsernameIgnoreCase(username))
                .thenReturn(Optional.of(user));

        final UserDto updatedUserDto = updatedTestUser.buildUserDto();
        authorizationManager.validateUserUpdate(updatedUserDto, username);

        validateSecurityServiceCall(OPERATOR_ADMIN);
    }

    @Test
    void validateUserUpdateNonExisting() {
        final User originalUser = CLIENT_NEW.buildUser();
        final String username = originalUser.getUsername();

        when(userRepository.findByUsernameIgnoreCase(username))
                .thenReturn(empty());

        final UserDto updatedUserDto = CLIENT_2_NEW.buildUserDto();
        authorizationManager.validateUserUpdate(updatedUserDto, username);

        validateSecurityServiceCall(CLIENT_ADMIN);
    }

    private void validateSecurityServiceCall(final Permission permission) {
        verify(securityService).authorizeAction(captor.capture());
        assertThat(captor.getValue()).isEqualByComparingTo(permission);
    }

    private static Stream<Arguments> roleChangeArguments () {
        return Stream.of(
                of(CLIENT_EXISTING, AUDIT_EXISTING),
                of(CLIENT_EXISTING, MANAGER_EXISTING),
                of(CLIENT_EXISTING, ADMIN_EXISTING),
                of(AUDIT_EXISTING, CLIENT_EXISTING),
                of(AUDIT_EXISTING, AUDIT_EXISTING),
                of(AUDIT_EXISTING, MANAGER_EXISTING),
                of(AUDIT_EXISTING, ADMIN_EXISTING),
                of(MANAGER_EXISTING, CLIENT_EXISTING),
                of(MANAGER_EXISTING, AUDIT_EXISTING),
                of(MANAGER_EXISTING, MANAGER_EXISTING),
                of(MANAGER_EXISTING, ADMIN_EXISTING),
                of(ADMIN_EXISTING, CLIENT_EXISTING),
                of(ADMIN_EXISTING, AUDIT_EXISTING),
                of(ADMIN_EXISTING, MANAGER_EXISTING),
                of(ADMIN_EXISTING, ADMIN_EXISTING));
    }
}