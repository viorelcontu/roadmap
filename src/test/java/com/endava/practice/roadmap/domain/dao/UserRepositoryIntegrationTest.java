package com.endava.practice.roadmap.domain.dao;

import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.util.TestUsers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void userExistByToken() {
        final TestUsers existing = TestUsers.MANAGER_EXISTING;
        final Optional<User> byTokenEquals = userDao.findByTokenEquals(existing.getToken());

        assertThat(byTokenEquals.map(User::getToken)).contains(existing.getToken());
        assertThat(byTokenEquals).contains(existing.buildUser());
    }

    @Test
    public void userDoesNotExistByToken() {
        final TestUsers nonExisting = TestUsers.CLIENT_NON_EXISTING;
        final Optional<User> byTokenEquals = userDao.findByTokenEquals(nonExisting.getToken());
        assertThat(byTokenEquals).isEmpty();
    }
}
