package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.mapper.EntityMapper;
import com.endava.practice.roadmap.domain.model.annotations.RequireContextualPermission;
import com.endava.practice.roadmap.domain.model.annotations.RequirePermission;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.exceptions.ResourceNotFoundException;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.endava.practice.roadmap.domain.model.enums.Permission.CLIENT_ADMIN;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final EntityMapper mapper;

    //TODO implement pagination and user queries
    @Override
    @RequirePermission(CLIENT_ADMIN)
    public List<UserDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::mapData)
                .collect(toList());
    }

    @Override
    @RequirePermission(CLIENT_ADMIN)
    public UserDto findOne(final String username) {
        User response = repository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> ResourceNotFoundException.ofUserName(username));
        return mapper.mapData(response);
    }

    @Override
    @Transactional
    @RequireContextualPermission
    public UserDto create(final UserDto dto) {
        final User user = mapper.mapData(dto);
        final User persistedUser = repository.save(user);
        return mapper.mapData(persistedUser);
    }

    @Override
    @Transactional
    @RequireContextualPermission
    public UserDto replace(final UserDto dto, final String username) {
        final User user = repository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> ResourceNotFoundException.ofUserName(username));

        mapper.updateUser(dto, user);
        final User persistedUser = repository.save(user);
        return mapper.mapData(persistedUser);
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
