package com.endava.practice.roadmap.service;

import com.endava.practice.roadmap.persistence.entity.IdEntity;
import com.endava.practice.roadmap.web.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractResourceService<T extends IdEntity<U>, U> implements ResourceOperations<T, U> {

    protected abstract JpaRepository<T, U> getDao();

    @Override
    public T findOne(U id) {
        return getDao().findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public T create(T resource) {
        return getDao().save(resource);
    }

    @Override
    public void update(final U id, final T resource) {
        getDao().save(resource);
    }

    @Override
    public void delete(final U id) {
        getDao().deleteById(id);
    }

    @Override
    public long count() {
        return getDao().count();
    }
}
