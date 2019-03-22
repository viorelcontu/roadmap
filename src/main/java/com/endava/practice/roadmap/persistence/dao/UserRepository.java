package com.endava.practice.roadmap.persistence.dao;

import com.endava.practice.roadmap.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
