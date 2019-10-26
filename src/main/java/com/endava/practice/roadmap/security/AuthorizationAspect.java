package com.endava.practice.roadmap.security;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.model.annotations.RequirePermission;
import com.endava.practice.roadmap.domain.model.entities.Group;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.enums.Permission;
import com.endava.practice.roadmap.domain.model.enums.Role;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.domain.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Predicate;

import static com.endava.practice.roadmap.domain.model.enums.Permission.CLIENT_ADMIN;
import static com.endava.practice.roadmap.domain.model.enums.Permission.OPERATOR_ADMIN;
import static com.endava.practice.roadmap.domain.model.enums.Role.CLIENT;

@Component
@Aspect
@RequiredArgsConstructor
public class AuthorizationAspect {

    private final SecurityService securityService;
    private final UserRepository userRepository;

    @Pointcut ("within(com.endava.practice.roadmap.domain.service.*)")
    private void servicePackages() {}

    @Pointcut ("(args(com.endava.practice.roadmap.domain.model.internal.UserDto,..))")
    private void userDtoMethods(){}

    @Pointcut ("@annotation(com.endava.practice.roadmap.domain.model.annotations.RequireContextualPermission)")
    private void contextualPermission() {}

    @Before("servicePackages() && @annotation(requiredPermission) && !userDtoMethods()")
    public void validatePermissions (RequirePermission requiredPermission) {
        securityService.authorizeAction(requiredPermission.value());
    }

    @Before("servicePackages() && contextualPermission() && args(userDto)")
    public void validateNewUser(UserDto userDto) {
        final Permission requiredPermission = userDto.getRole() == CLIENT ? CLIENT_ADMIN : OPERATOR_ADMIN;
        securityService.authorizeAction(requiredPermission);
    }

    @Before("servicePackages() && contextualPermission() && args(userDto, username)")
    @Transactional
    public void validateUserUpdate(UserDto userDto, String username) {
        final Optional<Role> originalRole = getUserRole(username);
        final Role updatedRole = userDto.getRole();

        final Permission requiredPermission = originalRole
            .filter(requiresHigherPermission(updatedRole))
            .map(role -> OPERATOR_ADMIN)
            .orElse(CLIENT_ADMIN);

        securityService.authorizeAction(requiredPermission);
    }

    private Predicate<Role> requiresHigherPermission(Role updatedRole) {
        return role -> role != CLIENT || updatedRole != CLIENT;
    }

    private Optional<Role> getUserRole(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
            .map(User::getGroup)
            .map(Group::getRole);
    }
}
