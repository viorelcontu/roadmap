package com.endava.practice.roadmap.service;

import com.endava.practice.roadmap.persistence.dao.UserRepository;
import com.endava.practice.roadmap.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractResourceService<User, Long> {

    private final UserRepository userRepository;

    @Override
    protected UserRepository getDao() {
        return userRepository;
    }
}
