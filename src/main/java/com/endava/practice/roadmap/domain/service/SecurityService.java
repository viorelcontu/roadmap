package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.enums.Permission;

public interface SecurityService {
    void authenticateUser (String token);

    void authorizeAction (Permission... requiredPermissions);

    User getCurrentUser();
}
