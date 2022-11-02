package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.entity.Film;

import java.util.Map;

/**
 * @author Oleg Khilko
 */

public interface FilmService {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Map<Integer, Film> getFilms();

    void deleteFilms();

    void deleteFilm(int filmId);
}
