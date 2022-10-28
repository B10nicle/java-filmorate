package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Khilko
 */

@Component
public class InMemoryFilmStorageImpl implements FilmStorage {
    private final Map<Integer, Film> films;
    private int id;

    public InMemoryFilmStorageImpl() {
        this.films = new HashMap<>();
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(++id);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else
            throw new DoesntExistException("Фильм: " + film.getDescription() + " не сущестует");
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }
}
