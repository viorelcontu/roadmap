package com.endava.practice.roadmap.service;

import com.endava.practice.roadmap.persistence.entity.IdEntity;
import com.endava.practice.roadmap.web.exception.BadRequestException;
import com.endava.practice.roadmap.web.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractResourceService<T extends IdEntity<U>, U> implements ResourceOperations<T, U> {

    protected abstract JpaRepository<T, U> getDao();

    @Override
    public T findOne(final U id) {
        return getDao().findById(id).orElseThrow(() -> ResourceNotFoundException.ofId(id));
    }

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public T create(final T resource) {
        return getDao().save(resource);
    }

    @Override
    public void update(final U id, final T resource) {
        if (!resource.getId().equals(id))
            throw BadRequestException.ofMismatchedPath(id, resource.getId());

        checkExistence(id);
        getDao().save(resource);
    }

    @Override
    public void delete(final U id) {
        checkExistence(id);
        getDao().deleteById(id);
    }

    @Override
    public long count() {
        return getDao().count();
    }

    private void checkExistence(final U id) {
        if (!getDao().existsById(id))
            throw ResourceNotFoundException.ofId(id);
    }
}
