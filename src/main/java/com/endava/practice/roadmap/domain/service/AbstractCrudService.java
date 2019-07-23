package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.model.exceptions.BadRequestException;
import com.endava.practice.roadmap.domain.model.exceptions.ResourceNotFoundException;
import com.endava.practice.roadmap.domain.model.entities.ResourceWithId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class AbstractCrudService<D extends ResourceWithId<U>, E extends ResourceWithId<U>, U> implements CrudOperations<D, U> {

    private final JpaRepository<E, U> dao;
    private final Function<D, E> toEntity;
    private final Function<E, D> toDto;

    @Override
    public D findOne(final U id) {
        E response = dao.findById(id).orElseThrow(() -> ResourceNotFoundException.ofId(id));
        return toDto.apply(response);
    }

    @Override
    public List<D> findAll() {
        return dao.findAll().stream()
                .map(toDto)
                .collect(toList());
    }

    @Override
    public D create(final D resource) {
        E entity = toEntity.apply(resource);
        return toDto.apply(dao.save(entity));
    }

    @Override
    @Transactional
    public void update(final U id, final D resource) {
        //FIXME in one transation checkExistence and dao.save!

        if (!resource.getId().equals(id))
            throw BadRequestException.ofMismatchedPath(id, resource.getId());

        checkExistence(id);
        E entity = toEntity.apply(resource);
        dao.save(entity);
    }

    @Override
    public void delete(final U id) {
        checkExistence(id);
        dao.deleteById(id);
    }

    @Override
    public long count() {
        return dao.count();
    }

    private void checkExistence(final U id) {
        if (!dao.existsById(id))
            throw ResourceNotFoundException.ofId(id);
    }
}
