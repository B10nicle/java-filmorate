package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.entity.Film;

import java.util.Optional;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface FilmRepository {

    Film createFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getFilms();

    Optional<Film> getFilmById(int filmId);

    void addLike(Film film, int userId);

    void deleteLike(Film film, int userId);

    List<Film> getPopularFilms(int count);
}
