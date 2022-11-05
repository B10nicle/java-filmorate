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
@Tag(name = "home-controller", description = "Homepage")
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
