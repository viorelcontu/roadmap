package com.endava.practice.roadmap.domain.dao;

import com.endava.practice.roadmap.domain.model.entities.CreditHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditHistoryRepository extends JpaRepository<CreditHistory, Integer> {
}
