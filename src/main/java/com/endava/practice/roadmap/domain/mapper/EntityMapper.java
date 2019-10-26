package com.endava.practice.roadmap.domain.mapper;

import com.endava.practice.roadmap.domain.dao.GroupRepository;
import com.endava.practice.roadmap.domain.model.entities.Group;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.enums.Role;
import com.endava.practice.roadmap.domain.model.exceptions.BadRequestException;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper
public abstract class EntityMapper {

    @Autowired
    protected GroupRepository groupRepository;

    @Mapping(target = "role", source = "group")
    public abstract UserDto mapData(User entity);

    @Mapping(target= "group", source ="role")
    public abstract User mapData(UserDto dto);

    @Mapping(target= "group", source ="role")
    public abstract void updateUser (UserDto dto, @MappingTarget User entity);

    public Group mapData(Role role) {
        Integer groupId = role.getGroupId();
        Optional<Group> optional = groupRepository.findById(groupId);
        return  optional.orElseThrow(() -> new BadRequestException ("Role does not exist"));
    };

    public Role mapData(Group group) {
        return group.getRole();
    };
}
