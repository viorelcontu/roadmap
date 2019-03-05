package com.endava.practice.roadmap.web.controller;

import com.endava.practice.roadmap.persistence.entity.IdEntity;
import com.endava.practice.roadmap.service.AbstractResourceService;
import com.endava.practice.roadmap.service.ResourceOperations;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class AbstractController<T extends IdEntity<U>, U> {

    protected abstract AbstractResourceService<T, U> getService();

    protected T findOne(final U id) {
        T resource = getService().findOne(id);
        log.info(ResourceOperations.FIND_ONE,resource);
        return resource;
    }

    protected List<T> findAll() {
        log.info(ResourceOperations.FIND_ALL);
        return getService().findAll();
    }

    protected T create(final T resource) {
        T result = getService().create(resource);
        log.info(ResourceOperations.CREATE, result);
        return result;
    }

    protected void update(final U id, final T resource) {
        log.info(ResourceOperations.UPDATE, resource);
        getService().update(id, resource);
    }

    protected void delete(final U id) {
        log.info(ResourceOperations.DELETE);
        getService().delete(id);
    }

    protected long count() {
        log.info(ResourceOperations.COUNT);
        return getService().count();
    }
}
