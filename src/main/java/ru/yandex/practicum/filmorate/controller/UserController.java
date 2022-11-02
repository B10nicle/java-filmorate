package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.service.ServiceAbs;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "user-controller", description = "User Service API")
public class UserController {
    private final ServiceAbs<User> service;

    public UserController(ServiceAbs<User> service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation("Get all users")
    public List<User> getUsers() {
        log.debug("List of all users: " + service.getAll());
        return new ArrayList<>(service.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation("Get user by ID")
    public User getUser(@PathVariable("id") int id) {
        log.debug("Request to get user: ID {}", id);
        return service.getById(id);
    }

    @PostMapping
    @ApiOperation("Create user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        log.debug("Request to create user: {}", user.getEmail());
        return service.add(user);
    }

    @PutMapping
    @ApiOperation("Update user")
    public User updateUser(@RequestBody User user) {
        log.debug("Request to update user: {}", user.getEmail());
        return service.update(user);
    }

    @DeleteMapping
    @ApiOperation("Delete all users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsers() {
        log.debug("Request to delete all users");
        service.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete user by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") int id) {
        log.debug("Request to delete user: ID {}", id);
        service.getById(id);
    }
}
