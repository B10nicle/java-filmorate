package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.service.Services;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * @author Oleg Khilko
 */

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "user-controller", description = "User Service API")
public class UserController {
    private final Services<User> service;

    public UserController(Services<User> service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get user by ID")
    public User getUser(@PathVariable("id") Long id) {
        log.debug("Request to get user: ID {}", id);
        return service.get(id);
    }

    @PostMapping
    @ApiOperation("Create user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        log.debug("Request to create user: {}", user.getEmail());
        return service.add(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete user by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) {
        log.debug("Request to delete user: ID {}", id);
        service.get(id);
    }
}
