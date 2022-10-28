package ru.yandex.practicum.filmorate.validator.film;

import ru.yandex.practicum.filmorate.entity.Film;

/**
 * @author Oleg Khilko
 */

public interface FilmValidator {
    void validate(Film film);
}
