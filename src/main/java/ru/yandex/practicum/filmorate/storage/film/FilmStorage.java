package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.entity.Film;

import java.util.Map;

/**
 * @author Oleg Khilko
 */

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Map<Integer, Film> getFilms();
}