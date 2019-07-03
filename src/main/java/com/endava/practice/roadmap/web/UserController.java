package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.dto.UserDto;
import com.endava.practice.roadmap.domain.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

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

    @GetMapping("/count")
    @Override
    public long count() {
        return super.count();
    }
}
