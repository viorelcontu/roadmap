package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.model.entity.ResourceWithId;

import java.util.List;

public interface CrudOperations<D extends ResourceWithId<U>, U> {

    String FIND_ONE = "Retrieving a resource {}";
    String FIND_ALL = "Retrieving all resources";
    String CREATE = "Creating a resource {}";
    String UPDATE = "Updating a resource {}";
    String DELETE = "Deleting a resource {}";
    String COUNT = "Counting resources";

    D findOne(final U id);

    List<D> findAll();

    D create(final D resource);

    void update(final U id, final D resource);

    void delete(final U id);

    long count();
}
