package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.service.film.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.entity.Film;
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
@RequestMapping("/films")
@Tag(name = "film-controller", description = "Film Service API")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.debug("List of all films: " + filmService.getFilms().values());
        return new ArrayList<>(filmService.getFilms().values());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@RequestBody Film film) {
        log.debug("Request to create film: {}", film);
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.debug("Request to update film: {}", film);
        return filmService.updateFilm(film);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilms() {
        log.debug("Request to delete all films");
        filmService.deleteFilms();
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable("id") int filmId) {
        log.debug("Request to delete film: ID {}", filmId);
        filmService.deleteFilm(filmId);
    }
}
