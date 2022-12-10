package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.service.film.FilmService;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.entity.Film;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService service;

    @PostMapping()
    public Film createFilm(@RequestBody Film film) {
        return service.createFilm(film);
    }

    @PutMapping()
    public Film updateFilm(@RequestBody Film film) {
        return service.updateFilm(film);
    }

    @GetMapping()
    public List<Film> getFilms() {
        return service.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        return service.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        service.addLike(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        service.deleteLike(userId, id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return service.getPopularFilms(count);
    }
}
