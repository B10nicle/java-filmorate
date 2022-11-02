package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorageImpl;
import ru.yandex.practicum.filmorate.exception.AlreadyAddedException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validator.film.FilmValidator;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Oleg Khilko
 */

@Service
public class InMemoryFilmServiceImpl implements FilmService {
    private final InMemoryFilmStorageImpl filmStorage;
    private final FilmValidator filmValidator;

    @Autowired
    public InMemoryFilmServiceImpl(InMemoryFilmStorageImpl filmStorage,
                                   FilmValidator filmValidator) {
        this.filmValidator = filmValidator;
        this.filmStorage = filmStorage;
    }

    @Override
    public Film addFilm(Film film) throws ValidationException {
        filmValidator.validate(film);

        if (filmStorage.getFilms() == null
                || filmStorage.getFilms().get(film.getId()) == null)
            return filmStorage.addFilm(film);
        else
            throw new AlreadyAddedException(
                    "Фильм: " + film.getDescription() + " уже добавлен");
    }

    @Override
    public Film updateFilm(Film film) throws ValidationException {
        filmValidator.validate(film);

        if (filmStorage.getFilms().get(film.getId()) == null)
            throw new DoesntExistException(
                    "Фильм: " + film.getDescription() + " не сущестует");

        return filmStorage.updateFilm(film);
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return filmStorage.getFilms();
    }

    @Override
    public void deleteFilms() {
        filmStorage.deleteFilms();
    }

    @Override
    public void deleteFilm(int filmId) {
        filmStorage.deleteFilm(filmId);
    }
}
