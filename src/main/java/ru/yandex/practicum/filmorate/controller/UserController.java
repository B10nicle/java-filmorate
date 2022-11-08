package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Controller
@RequestMapping("/users")
@Tag(name = "user-controller", description = "User Service API")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    @ApiOperation("Show all users")
    public String getUsers(Model model,
                           @Param("keyword") String keyword) {
        var users = service.search(keyword);

        if (users.isEmpty())
            return "search-error";

        model.addAttribute("keyword", keyword);
        model.addAttribute("users", users);
        log.debug("List of all users: " + users);
        return "users";
    }

    @GetMapping("/add-user")
    @ApiOperation("Show form to add a new user")
    public String showAddForm(Model model) {
        var user = new User();
        log.debug("Request to show add form for user: {}", user);
        model.addAttribute("user", user);
        return "add-user";
    }

    @PostMapping("/add")
    @ApiOperation("Add user")
    public String addUser(@Valid @ModelAttribute("user") User user) {
        log.debug("Request to create user: {}", user);
        service.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    @ApiOperation("Show form to edit an user")
    public ModelAndView showEditForm(@PathVariable Long id) {
        var editView = new ModelAndView("edit-user");
        var user = service.getById(id);
        editView.addObject("user", user);
        return editView;
    }

    @PostMapping("/{id}/edit")
    @ApiOperation("Edit user")
    public String editUser(@Valid @ModelAttribute("user") User user) {
        log.debug("Request to edit user: {}", user);
        service.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    @ApiOperation("Delete user")
    public String deleteUser(@PathVariable Long id) {
        log.debug("Request to delete user: ID {}", id);
        service.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/friends")
    @ApiOperation("Get the list of friends")
    public String getFriends(Model model,
                             @PathVariable Long id) {
        var friends = service.getFriends(id);
        model.addAttribute("friends", friends);
        log.debug("Request to get the list of friends of user: ID {}", id);
        return "friends";
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ApiOperation("Get the list of all common friends")
    public String getCommonFriends(Model model,
                                   @PathVariable Long id,
                                   @PathVariable Long otherId) {
        var commonFriends = service.getCommonFriends(id, otherId);
        model.addAttribute("commonFriends", commonFriends);
        log.debug("Request to get the list of all common friends of user: ID {}", id);
        return "common-friends";
    }

    @PostMapping("/{id}/friends/{friendId}")
    @ApiOperation("Add friend")
    public String addFriend(@PathVariable Long id,
                            @PathVariable Long friendId) {
        log.debug("User with ID: {} added a friend with ID: {}", id, friendId);
        service.addFriend(id, friendId);
        return "redirect:/users/{id}/friends";
    }

    @GetMapping("/{id}/friends/{friendId}/delete")
    @ApiOperation("Delete friend")
    public String deleteFriend(@PathVariable Long id,
                               @PathVariable Long friendId) {
        log.debug("User with ID: {} deleted a friend with ID: {}", id, friendId);
        service.deleteFriend(id, friendId);
        return "redirect:/users/{id}/friends";
    }
}
