package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Controller
@Tag(name = "main-controller", description = "Main")
public class MainController {

    @GetMapping("/")
    public String home() {
        log.debug("Request to show homepage");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        log.debug("Request to show login page");
        return "login";
    }
}