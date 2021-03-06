package com.endava.practice.roadmap.domain.dao;

import com.endava.practice.roadmap.domain.model.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
