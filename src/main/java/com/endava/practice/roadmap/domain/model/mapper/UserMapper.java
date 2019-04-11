package com.endava.practice.roadmap.domain.model.mapper;

import com.endava.practice.roadmap.domain.model.dto.UserDto;
import com.endava.practice.roadmap.domain.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);
    User toEntity(UserDto dto);
}
