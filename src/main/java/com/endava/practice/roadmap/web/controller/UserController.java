package com.endava.practice.roadmap.web.controller;

import com.endava.practice.roadmap.persistence.entity.User;
import com.endava.practice.roadmap.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Api(description = "Perform CRUD operations on API users")
public class UserController extends AbstractController<User, Long> {

    private final UserService userService;

    @Override
    protected UserService getService() {
        return userService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a specific user by id")
    @Override
    @ResponseBody
    public User findOne(@PathVariable("id") final Long id) {
        return super.findOne(id);
    }

    @GetMapping
    @ApiOperation(value = "List all the users")
    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @PostMapping
    @ApiOperation(value = "Create new user")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public User create(@RequestBody final User resource) {
        return super.create(resource);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an user by id", notes = "currently the API does not verify if id from Pathvariable and request body is the same!!!")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Override
    public void update(@PathVariable("id") final Long id, @RequestBody final User user) {
        super.update(id, user);
    }

    @DeleteMapping("/{id}")
    @Override
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") final Long id) {
        super.delete(id);
    }

    @GetMapping("/count")
    @Override
    public long count() {
        return super.count();
    }
}
