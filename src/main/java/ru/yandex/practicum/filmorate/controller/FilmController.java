package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.service.Services;
import org.springframework.stereotype.Controller;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Controller
@Tag(name = "film-controller", description = "Film Service API")
public class FilmController {
    private final Services<Film> service;

    public FilmController(Services<Film> service) {
        this.service = service;
    }

    @GetMapping("/films")
    @ApiOperation("Show all films")
    public String getFilms(Model model) {
        var films = service.getAll();
        model.addAttribute("films", films);
        log.debug("List of all films: " + films);
        return "films";
    }

    @GetMapping("films/add")
    @ApiOperation("Show form to add a new film")
    public String showFormToAddFilm() {
        return "add-film-form";
    }

    @PostMapping("films/add")
    @ApiOperation("Add film")
    public String addFilm(@Valid
                          @RequestParam String description,
                          @RequestParam LocalDate date,
                          @RequestParam int duration,
                          @RequestParam String name) {
        var film = new Film(name, description, duration, date);
        log.debug("Request to create film: {}", film);
        service.add(film);
        return "redirect:/films";
    }

    @GetMapping("films/{id}")
    @ApiOperation("Get film by ID")
    public String getFilmById(@PathVariable("id") Long id, Model model) {
        var film = service.getById(id);
        model.addAttribute("film", film);
        log.debug("Request to get film: ID {}", id);
        return "film";
    }

    @PutMapping
    @ApiOperation("Update film")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.debug("Request to update film: {}", film);
        return service.update(film);
    }

    @DeleteMapping("/films")
    @ApiOperation("Delete all films")
    public String deleteFilms(Model model) {
        log.debug("Request to delete all films");
        service.deleteAll();
        var films = service.getAll();
        model.addAttribute("films", films);
        return "delete-films";
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete film by ID")
    public void deleteFilm(@PathVariable("id") Long id) {
        log.debug("Request to delete film: ID {}", id);
        service.deleteById(id);
    }
}
