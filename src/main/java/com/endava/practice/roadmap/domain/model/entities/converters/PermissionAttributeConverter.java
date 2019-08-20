package com.endava.practice.roadmap.domain.model.entities.converters;

import com.endava.practice.roadmap.domain.model.enums.Permission;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static com.endava.practice.roadmap.domain.model.enums.Permission.idPermissionMap;

@Converter
public class PermissionAttributeConverter implements AttributeConverter<Permission, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Permission permission) {
        return permission.getPermissionId();
    }

    @Override
    public Permission convertToEntityAttribute(Integer permissionId) {
        return idPermissionMap.get(permissionId);
    }
}
