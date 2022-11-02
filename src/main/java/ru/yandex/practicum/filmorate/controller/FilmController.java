package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.service.ServiceAbs;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Slf4j
@RestController
@RequestMapping("/films")
@Tag(name = "film-controller", description = "Film Service API")
public class FilmController {
    private final ServiceAbs<Film> service;

    public FilmController(ServiceAbs<Film> service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation("Get all films")
    public List<Film> getFilms() {
        log.debug("List of all films: " + service.getAll());
        return new ArrayList<>(service.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation("Get film by ID")
    public Film getFilm(@PathVariable("id") int id) {
        log.debug("Request to get film: ID {}", id);
        return service.getById(id);
    }

    @PostMapping
    @ApiOperation("Create film")
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        log.debug("Request to create film: {}", film);
        return service.add(film);
    }

    @PutMapping
    @ApiOperation("Update film")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.debug("Request to update film: {}", film);
        return service.update(film);
    }

    @DeleteMapping
    @ApiOperation("Delete all films")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilms() {
        log.debug("Request to delete all films");
        service.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete film by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable("id") int id) {
        log.debug("Request to delete film: ID {}", id);
        service.deleteById(id);
    }
}
