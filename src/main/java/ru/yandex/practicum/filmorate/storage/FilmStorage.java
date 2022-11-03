package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Khilko
 */

@Component
public class FilmStorage extends Storage<Film> {
    private final Map<Integer, Film> films;
    private int id;

    public FilmStorage() {
        this.films = new HashMap<>();
    }

    @Override
    public Film add(Film film) {
        film.setId(++id);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Map<Integer, Film> getAll() {
        return films;
    }

    @Override
    public Film getById(int id) {
        return films.get(id);
    }

    @Override
    public void deleteAll() {
        films.clear();
    }

    @Override
    public void deleteById(int id) {
        films.remove(id);
    }
}
