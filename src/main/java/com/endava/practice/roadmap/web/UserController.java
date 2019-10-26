package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public UserDto findOne(@PathVariable("username") final String userName) {
        return userService.findOne(userName);
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDto create(@RequestBody @Valid final UserDto resource) {
        return userService.create(resource);
    }

    @PutMapping("/{username}")
    @ResponseStatus(ACCEPTED)
    public UserDto replace(@PathVariable("username") final String userName, @RequestBody @Valid final UserDto user) {
        return userService.replace(user, userName);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable("username") final String userName) {
        userService.delete(userName);
    }
}
