package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.service.genres.GenresService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.entity.Genre;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final GenresService service;

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable int id) {
        return service.getGenre(id);
    }

    @GetMapping()
    public List<Genre> getAll() {
        return service.getAllGenres();
    }
}
