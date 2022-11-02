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
        boolean exists = filmStorage.getFilms().containsKey(film.getId());
        boolean isEmpty = filmStorage.getFilms().isEmpty();
        filmValidator.validate(film);

        if (isEmpty)
            return filmStorage.addFilm(film);

        if (!exists)
            throw new AlreadyAddedException(
                    "Фильм: " + film.getDescription() + " уже добавлен");

        return filmStorage.addFilm(film);
    }

    @Override
    public Film updateFilm(Film film) throws ValidationException {
        boolean exists = filmStorage.getFilms().containsKey(film.getId());
        filmValidator.validate(film);

        if (!exists)
            throw new DoesntExistException(
                    "Фильм: " + film.getDescription() + " не существует");

        return filmStorage.updateFilm(film);
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return filmStorage.getFilms();
    }

    @Override
    public Film getFilm(int filmId) {
        boolean exists = filmStorage.getFilms().containsKey(filmId);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно получить несуществующий фильм");

        return filmStorage.getFilm(filmId);
    }

    @Override
    public void deleteFilms() {
        boolean isEmpty = filmStorage.getFilms().isEmpty();

        if (isEmpty)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующие фильмы");

        filmStorage.deleteFilms();
    }

    @Override
    public void deleteFilm(int filmId) {
        boolean exists = filmStorage.getFilms().containsKey(filmId);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующий фильм");

        filmStorage.deleteFilm(filmId);
    }
}
