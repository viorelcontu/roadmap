package com.endava.practice.roadmap.jpatest;

import com.endava.practice.roadmap.domain.dao.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import lombok.val;

import static com.endava.practice.roadmap.util.TestUsers.CLIENT_EXISTING;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserJpaTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsernameAndToken(){

        val clientUser = CLIENT_EXISTING.buildUser();

        userRepository.save(clientUser);

        val result = userRepository.findByUsernameAndToken(clientUser.getUsername(), clientUser.getToken());

        assertEquals(Optional.of(clientUser), result);

    }
}
