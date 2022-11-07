package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.AlreadyAddedException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.validator.Validator;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Service
public class FilmService extends ServiceAbs<Film> {
    private final Validator<Film> validator;
    private final Storage<Film> storage;

    public FilmService(Validator<Film> validator,
                       Storage<Film> storage) {
        this.validator = validator;
        this.storage = storage;
    }

    @Override
    public Film add(Film film) {
        boolean exists = storage.getAll().containsKey(film.getId());
        boolean storageIsEmpty = storage.getAll().isEmpty();
        validator.validate(film);

        if (storageIsEmpty)
            return storage.add(film);

        if (!exists)
            throw new AlreadyAddedException(
                    "Фильм: " + film.getDescription() + " уже добавлен");

        return storage.add(film);
    }

    @Override
    public Film update(Film film) {
        boolean exists = storage.getAll().containsKey(film.getId());
        validator.validate(film);

        if (!exists)
            throw new DoesntExistException(
                    "Фильм: " + film.getDescription() + " не существует");

        return storage.update(film);
    }

    @Override
    public Film getById(Long id) {
        boolean exists = storage.getAll().containsKey(id);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно получить несуществующий фильм");

        return storage.getById(id);
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(storage.getAll().values());
    }

    @Override
    public void deleteAll() {
        boolean storageIsEmpty = storage.getAll().isEmpty();

        if (storageIsEmpty)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующие фильмы");

        storage.deleteAll();
    }

    @Override
    public void delete(Long id) {
        boolean exists = storage.getAll().containsKey(id);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующий фильм");

        storage.deleteById(id);
    }

    public void addLike(Long userId, Long filmId) {
        boolean userDoesntExist = storage.getAll().get(userId) == null;
        boolean filmDoesntExist = storage.getAll().get(filmId) == null;

        if (userDoesntExist)
            throw new DoesntExistException(
                    "Данного пользователя не существует");

        if (filmDoesntExist)
            throw new DoesntExistException(
                    "Данного фильма не существует");

        storage.getById(filmId).getLikes().add(userId);
    }

    public void deleteLike(Long userId, Long filmId) {
        boolean userDoesntExist = storage.getAll().get(userId) == null;
        boolean filmDoesntExist = storage.getAll().get(filmId) == null;
        boolean storageIsEmpty = storage.getAll().isEmpty();

        if (userDoesntExist || storageIsEmpty)
            throw new DoesntExistException(
                    "Данного пользователя не существует");

        if (filmDoesntExist)
            throw new DoesntExistException(
                    "Данного фильма не существует");

        storage.getById(filmId).getLikes().remove(userId);
    }

    public List<Film> getPopularFilms(int amount) {
        var films = storage.getAll().values();

        return films.stream()
                .sorted()
                .limit(amount)
                .collect(Collectors.toList());
    }
}
