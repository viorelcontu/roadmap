package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.enums.Permission;
import com.endava.practice.roadmap.domain.model.exceptions.AuthenticationException;
import com.endava.practice.roadmap.domain.model.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.UUID.fromString;

@Service
@RequiredArgsConstructor
@Profile("!no-security")
public class SecurityServiceImpl implements SecurityService {

    private ThreadLocal<User> currentUser = new ThreadLocal<>();
    private ThreadLocal<Set<Permission>> currentUserPermissions = new ThreadLocal<>();

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void authenticateUser(final String token) {
        final User user = ofNullable(token)
                .map(this::validateStringToken)
                .flatMap(userRepository::findByTokenEquals)
                .filter(User::getActive)
                .orElseThrow(AuthenticationException::new);

        currentUser.set(user);
        currentUserPermissions.set(new HashSet<>(user.getGroup().getPermissions()));
    }

    @Override
    public void authorizeAction(final Permission... requiredPermissions) {
        final Set<Permission> userPermissions = ofNullable(currentUserPermissions.get())
                .orElseThrow(ForbiddenException::new);

        final boolean accessDenied = Stream.of(requiredPermissions).noneMatch(userPermissions::contains);
        if (accessDenied) throw new ForbiddenException();
    }

    @Override
    public User getCurrentUser() {
        return ofNullable(currentUser.get()).orElseThrow(AuthenticationException::new);
    }

    private UUID validateStringToken(final String token) {
        try {
            return fromString(token);
        } catch (IllegalArgumentException ex) {
            throw new AuthenticationException();
        }
    }
}
