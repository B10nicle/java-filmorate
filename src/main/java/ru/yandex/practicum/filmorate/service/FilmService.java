package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.validator.Validator;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Service;

/**
 * @author Oleg Khilko
 */

@Service
public class FilmService extends Services<Film> {
    private final Validator<Film> validator;
    private final FilmRepository repository;

    public FilmService(Validator<Film> validator,
                       FilmRepository repository) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public Film add(Film film) {
        validator.validate(film);
        return repository.save(film);
    }

    @Override
    public Film update(Film film) {
        var optionalFilm = repository.findById(film.getId());
        validator.validate(film);

        if (optionalFilm.isEmpty())
            throw new DoesntExistException(
                    "Фильм: " + film.getDescription() + " не существует");

        return repository.save(optionalFilm.get());
    }

    @Override
    public Film getById(Long id) {
        var film = repository.findById(id);
        if (film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующий фильм");

        return film.get();
    }

    @Override
    public Iterable<Film> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        boolean isEmpty = repository.count() == 0;

        if (isEmpty)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующие фильмы");

        repository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        var film = repository.findById(id);

        if (film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно удалить несуществующий фильм");

        repository.deleteById(id);
    }
}
