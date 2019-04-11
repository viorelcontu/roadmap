package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.entity.ResourceWithId;
import com.endava.practice.roadmap.domain.service.CrudOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCrudController<D extends ResourceWithId<U>, U> {

    protected abstract CrudOperations<D, U> getService();

    protected D findOne(final U id) {
        D resource = getService().findOne(id);
        log.info(CrudOperations.FIND_ONE,resource);
        return resource;
    }

    protected List<D> findAll() {
        log.info(CrudOperations.FIND_ALL);
        return getService().findAll();
    }

    protected D create(final D resource) {
        D result = getService().create(resource);
        log.info(CrudOperations.CREATE, result);
        return result;
    }

    protected void update(final U id, final D resource) {
        log.info(CrudOperations.UPDATE, resource);
        getService().update(id, resource);
    }

    protected void delete(final U id) {
        log.info(CrudOperations.DELETE);
        getService().delete(id);
    }

    protected long count() {
        log.info(CrudOperations.COUNT);
        return getService().count();
    }
}
