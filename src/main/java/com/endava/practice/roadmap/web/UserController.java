package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.dto.UserDto;
import com.endava.practice.roadmap.domain.service.AbstractCrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Api(description = "Perform CRUD operations on API users")
public class UserController extends AbstractCrudController<UserDto, Long> {

    private final AbstractCrudService userService;

    @Override
    protected AbstractCrudService getService() {
        return userService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a specific user by id")
    @Override
    public UserDto findOne(@PathVariable("id") final Long id) {
        return super.findOne(id);
    }

    @GetMapping
    @ApiOperation(value = "List all the users")
    @Override
    public List<UserDto> findAll() {
        return super.findAll();
    }

    @PostMapping
    @ApiOperation(value = "Create new user")
    @ResponseStatus(CREATED)
    @Override
    public UserDto create(@RequestBody @Valid final UserDto resource) {
        return super.create(resource);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an user by id")
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
