package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.service.film.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        log.debug("Фильм " + film.getName() + " добавлен");
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.debug("Фильм " + film.getName() + " обновлён");
        return filmService.updateFilm(film);
    }

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Список всех фильмов: " + filmService.getFilms().values());
        return new ArrayList<>(filmService.getFilms().values());
    }
}
