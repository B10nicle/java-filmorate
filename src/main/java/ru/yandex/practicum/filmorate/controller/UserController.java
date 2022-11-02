package ru.yandex.practicum.filmorate.controller;

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
    public List<User> getUsers() {
        log.debug("List of all users: " + userService.getUsers().values());
        return new ArrayList<>(userService.getUsers().values());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        log.debug("Request to create user: {}", user.getEmail());
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.debug("Request to update user: {}", user.getEmail());
        return userService.updateUser(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsers() {
        log.debug("Request to delete all users");
        userService.deleteUsers();
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") int userId) {
        log.debug("Request to delete user: ID {}", userId);
        userService.deleteUser(userId);
    }
}
