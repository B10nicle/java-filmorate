package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.entity.Film;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface FilmService {

    Film createFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getFilms();

    Film getFilmById(int filmId);

    void addLike(int userId, int filmId);

    void deleteLike(int userId, int filmId);

    List<Film> getPopularFilms(int count);
}
