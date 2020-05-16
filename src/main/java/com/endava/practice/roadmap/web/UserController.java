package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.internal.UserDto;
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

@RestController
@RequestMapping("/users")
public interface UserController {

    @GetMapping
    List<UserDto> findAll();

    @GetMapping("/{username}")
    UserDto find(@PathVariable("username") String username);

    @PostMapping
    @ResponseStatus(CREATED)
    UserDto create(@RequestBody @Valid UserDto resource);

    @PutMapping("/{username}")
    @ResponseStatus(ACCEPTED)
    UserDto amend(@PathVariable("username") String username, @RequestBody @Valid UserDto user);

    @DeleteMapping("/{username}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable("username") String userName);
}
