package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.domain.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController extends AbstractCrudController<UserDto, Long> {

    //TODO add token management for users
    //TODO add api-credit management for users.

    private final AbstractCrudService userService;

    @Override
    protected AbstractCrudService getService() {
        return userService;
    }

    @GetMapping("/{id}")
    @Override
    public UserDto findOne(@PathVariable("id") final Long id) {
        return super.findOne(id);
    }

    @GetMapping
    @Override
    public List<UserDto> findAll() {
        return super.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Override
    public UserDto create(@RequestBody @Valid final UserDto resource) {
        return super.create(resource);
    }

    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    @Override
    public void update(@PathVariable("id") final Long id, @RequestBody @Valid final UserDto user) {
        super.update(id, user);
    }

    @DeleteMapping("/{id}")
    @Override
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable("id") final Long id) {
        super.delete(id);
    }
}
