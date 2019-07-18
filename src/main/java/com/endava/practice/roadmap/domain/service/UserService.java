package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.mapper.UserMapper;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCrudService<UserDto, User, Long> {

    public UserService(JpaRepository<User, Long> userRepository, UserMapper userMapper) {
        super(userRepository, userMapper::toEntity, userMapper::toDto);
    }
}
