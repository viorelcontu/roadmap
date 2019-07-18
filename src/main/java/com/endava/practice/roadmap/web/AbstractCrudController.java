package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.entities.ResourceWithId;
import com.endava.practice.roadmap.domain.service.CrudOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.endava.practice.roadmap.domain.service.CrudOperations.COUNT;
import static com.endava.practice.roadmap.domain.service.CrudOperations.CREATE;
import static com.endava.practice.roadmap.domain.service.CrudOperations.DELETE;
import static com.endava.practice.roadmap.domain.service.CrudOperations.FIND_ALL;
import static com.endava.practice.roadmap.domain.service.CrudOperations.FIND_ONE;
import static com.endava.practice.roadmap.domain.service.CrudOperations.UPDATE;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCrudController<D extends ResourceWithId<U>, U> {

    protected abstract CrudOperations<D, U> getService();

    protected D findOne(final U id) {
        D resource = getService().findOne(id);
        log.info(FIND_ONE,resource);
        return resource;
    }

    protected List<D> findAll() {
        log.info(FIND_ALL);
        return getService().findAll();
    }

    protected D create(final D resource) {
        D result = getService().create(resource);
        log.info(CREATE, result);
        return result;
    }

    protected void update(final U id, final D resource) {
        log.info(UPDATE, resource);
        getService().update(id, resource);
    }

    protected void delete(final U id) {
        log.info(DELETE);
        getService().delete(id);
    }

    protected long count() {
        log.info(COUNT);
        return getService().count();
    }
}
