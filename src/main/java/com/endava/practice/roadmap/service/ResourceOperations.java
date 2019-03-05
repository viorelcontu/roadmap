package com.endava.practice.roadmap.service;

import com.endava.practice.roadmap.persistence.entity.IdEntity;

import java.util.List;

public interface ResourceOperations<T extends IdEntity<U>, U> {

    String FIND_ONE = "Retrieving a resource {}";
    String FIND_ALL = "Retrieving all resources";
    String CREATE = "Creating a resource {}";
    String UPDATE = "Updating a resource {}";
    String DELETE = "Deleting a resource {}";
    String COUNT = "Counting resources";

    T findOne(U id);

    List<T> findAll();

    T create(T resource);

    void update(U id, T resource);

    void delete(U id);

    long count();
}
