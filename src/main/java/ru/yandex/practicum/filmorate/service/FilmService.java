package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.AlreadyAddedException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.validator.Validator;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Service;

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
        boolean isEmpty = storage.getAll().isEmpty();
        validator.validate(film);

        if (isEmpty)
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
    public Film getById(int id) {
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
        boolean isEmpty = storage.getAll().isEmpty();

        if (isEmpty)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующие фильмы");

        storage.deleteAll();
    }

    @Override
    public void deleteById(int id) {
        boolean exists = storage.getAll().containsKey(id);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующий фильм");

        storage.deleteById(id);
    }
}
