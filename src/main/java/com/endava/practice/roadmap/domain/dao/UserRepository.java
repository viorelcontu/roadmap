package com.endava.practice.roadmap.domain.dao;

import com.endava.practice.roadmap.domain.model.entities.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByTokenEquals(UUID token);

    Boolean existsByUsernameIgnoreCase(String username);

    Integer deleteUserByUsernameIgnoreCase(String username);
}
