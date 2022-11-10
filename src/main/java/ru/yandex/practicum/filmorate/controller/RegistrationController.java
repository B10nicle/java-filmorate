package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.filmorate.dto.UserRegistrationDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.service.UserService;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/registration")
@Tag(name = "registration-controller", description = "User Registration API")
public class RegistrationController {
    private final UserService service;

    public RegistrationController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation("show registration form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping
    @ApiOperation("register user account")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserRegistrationDto userDto) {
        log.debug("Request to register userDto: {}", userDto);
        service.save(userDto);
        return "redirect:/registration?success";
    }
}
