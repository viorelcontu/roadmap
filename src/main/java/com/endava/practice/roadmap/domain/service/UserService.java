package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.model.internal.UserDto;

import java.util.List;

public interface UserService {
    UserDto findOne(String userName);

    List<UserDto> findAll();

    UserDto create(UserDto dto);

    UserDto replace(UserDto dto, String userName);

    void delete(String userName);
}
