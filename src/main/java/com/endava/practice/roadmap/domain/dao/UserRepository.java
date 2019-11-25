package com.endava.practice.roadmap.domain.dao;

import com.endava.practice.roadmap.domain.model.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String userName);

    Optional<User> findByTokenEquals(UUID token);

    Boolean existsByUsernameIgnoreCase(String userName);

    Integer deleteUserByUsernameIgnoreCase(String userName);

    Optional<User> findByUsernameAndToken(String userName, UUID token);
}
