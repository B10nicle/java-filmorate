package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.service.UserService;
import org.springframework.stereotype.Controller;
import ru.yandex.practicum.filmorate.dto.UserDto;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/registration")
@Tag(name = "registration-controller", description = "User Registration API")
public class RegistrationController {
    private final UserService service;

    @GetMapping
    @ApiOperation("show registration form")
    public String showRegistrationForm(Model model) {
        log.debug("Request to show registration form");
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping
    @ApiOperation("register user account")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserDto userDto) {
        log.debug("Request to register userDto: {}", userDto);
        service.save(userDto);
        return "redirect:/registration?success";
    }
}
