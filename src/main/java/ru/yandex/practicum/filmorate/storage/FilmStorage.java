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
    private final Map<Long, Film> films;
    private Long id;

    public FilmStorage() {
        this.films = new HashMap<>();
        this.id = 0L;
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
    public Map<Long, Film> getAll() {
        return films;
    }

    @Override
    public Film getById(Long id) {
        return films.get(id);
    }

    @Override
    public void deleteAll() {
        films.clear();
    }

    @Override
    public void deleteById(Long id) {
        films.remove(id);
    }
}
