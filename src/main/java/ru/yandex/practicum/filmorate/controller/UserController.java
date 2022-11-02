package ru.yandex.practicum.filmorate.controller;

import io.swagger.annotations.ApiOperation;
import ru.yandex.practicum.filmorate.entity.Film;
import ru.yandex.practicum.filmorate.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation("Get all users")
    public List<User> getUsers() {
        log.debug("List of all users: " + userService.getUsers().values());
        return new ArrayList<>(userService.getUsers().values());
    }

    @GetMapping("/{id}")
    @ApiOperation("Get user by ID")
    public User getUser(@PathVariable("id") int userId) {
        log.debug("Request to get user: ID {}", userId);
        return userService.getUser(userId);
    }

    @PostMapping
    @ApiOperation("Create user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        log.debug("Request to create user: {}", user.getEmail());
        return userService.addUser(user);
    }

    @PutMapping
    @ApiOperation("Update user")
    public User updateUser(@RequestBody User user) {
        log.debug("Request to update user: {}", user.getEmail());
        return userService.updateUser(user);
    }

    @DeleteMapping
    @ApiOperation("Delete all users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsers() {
        log.debug("Request to delete all users");
        userService.deleteUsers();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete user by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") int userId) {
        log.debug("Request to delete user: ID {}", userId);
        userService.deleteUser(userId);
    }
}
