package com.endava.practice.roadmap.domain.mapper;

import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto toDto(User entity);
    User toEntity(UserDto dto);
}
