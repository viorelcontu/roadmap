package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.converter.UserConverter;
import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.model.annotations.RequireContextualPermission;
import com.endava.practice.roadmap.domain.model.annotations.RequirePermission;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.exceptions.ResourceNotFoundException;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.web.UserController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.endava.practice.roadmap.domain.model.enums.Permission.CLIENT_ADMIN;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class UserService implements UserController {

    //TODO implement pagination and user queries

    private final UserRepository repository;
    private final UserConverter mapper;

    @Override
    @RequirePermission(CLIENT_ADMIN)
    public List<UserDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @Override
    @RequirePermission(CLIENT_ADMIN)
    public UserDto find(final String username) {
        User response = repository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> ResourceNotFoundException.ofUserName(username));
        return mapper.toDto(response);
    }

    @Override
    @Transactional
    @RequireContextualPermission
    public UserDto create(final UserDto dto) {
        final User user = mapper.toEntity(dto);
        final User persistedUser = repository.save(user);
        return mapper.toDto(persistedUser);
    }

    @Override
    @Transactional
    @RequireContextualPermission
    public UserDto amend(final String username, final UserDto dto) {
        final User user = repository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> ResourceNotFoundException.ofUserName(username));

        mapper.updateUser(dto, user);
        final User persistedUser = repository.save(user);
        return mapper.toDto(persistedUser);
    }

    @Override
    @Transactional
    @RequirePermission(CLIENT_ADMIN)
    public void delete(final String username) {
        checkExistence(username);
        repository.deleteUserByUsernameIgnoreCase(username);
    }

    private void checkExistence(final String username) {
        if (!repository.existsByUsernameIgnoreCase(username))
            throw ResourceNotFoundException.ofUserName(username);
    }
}
