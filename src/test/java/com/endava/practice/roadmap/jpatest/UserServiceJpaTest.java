package com.endava.practice.roadmap.jpatest;

import com.endava.practice.roadmap.config.UserServiceConfiguration;
import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.mapper.EntityMapper;
import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.enums.Role;
import com.endava.practice.roadmap.domain.service.internalservice.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import lombok.val;

import static com.endava.practice.roadmap.util.TestUsers.CLIENT_EXISTING;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = UserServiceConfiguration.class)
@MockBean(EntityMapper.class)
public class UserServiceJpaTest {

    public static final boolean ACTIVE = true;
    public static final UUID TOKEN = UUID.randomUUID();
    public static final BigDecimal BALANCE = BigDecimal.valueOf(100);
    public static final int CREDITS = 5;
    public static final String NICKNAME = "nick";
    public static final String USERNAME = "user";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testGetUserByNameAndToken() {

        val clientUser = CLIENT_EXISTING.buildUser();

        val result = userService.getUserByNameAndToken(clientUser.getUsername(), clientUser.getToken());

        assertEquals(Optional.of(clientUser), result);
    }

    @Test
    public void testDeleteUserByUsernameIgnoreCase() {
        val user = User.builder()
            .username(USERNAME)
            .nickname(NICKNAME)
            .credits(CREDITS)
            .group(Role.ADMIN.getEntity())
            .balance(BALANCE)
            .token(TOKEN)
            .active(ACTIVE)
            .build();

        entityManager.persistAndFlush(user);

        val result = userRepository.deleteUserByUsernameIgnoreCase(user.getUsername());

        assertEquals(1, result.intValue());
    }
}
