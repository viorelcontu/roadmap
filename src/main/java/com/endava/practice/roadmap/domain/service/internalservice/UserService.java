package com.endava.practice.roadmap.domain.service.internalservice;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.mapper.EntityMapper;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.exceptions.ResourceNotFoundException;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final EntityMapper mapper;

    public UserDto findOne(final String userName) {
        User response = repository.findByUsernameIgnoreCase(userName)
                .orElseThrow(() -> ResourceNotFoundException.ofUserName(userName));
        return mapper.mapData(response);
    }

    public List<UserDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::mapData)
                .collect(toList());
    }

    @Transactional
    public UserDto create(final UserDto dto) {
        final User user = mapper.mapData(dto);
        final User persistedUser = repository.save(user);
        return mapper.mapData(persistedUser);
    }

    @Transactional
    public UserDto replace(final String userName, final UserDto dto) {
        final User user = repository.findByUsernameIgnoreCase(userName)
                .orElseThrow(() -> ResourceNotFoundException.ofUserName(userName));

        mapper.updateUser(dto, user);
        final User persistedUser = repository.save(user);
        return mapper.mapData(persistedUser);
    }

    @Transactional
    public void delete(final String userName) {
        checkExistence(userName);
        repository.deleteUserByUsernameIgnoreCase(userName);
    }

    private void checkExistence(final String userName) {
        if (!repository.existsByUsernameIgnoreCase(userName))
            throw ResourceNotFoundException.ofUserName(userName);
    }

    public Optional<User> getUserByNameAndToken(String userName, UUID token){
        return repository.findByUsernameAndToken(userName, token);
    }

    public User saveUser(User user){
        return repository.save(user);
    }

}
