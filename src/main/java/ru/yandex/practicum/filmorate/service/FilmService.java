package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.validator.Validator;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Film get(Long id) {
        var film = repository.findById(id);
        if (film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующий фильм");

        return film.get();
    }

    @Override
    public List<Film> search(String keyword) {
        if (keyword != null)
            return repository.search(keyword);

        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        var film = repository.findById(id);

        if (film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно удалить несуществующий фильм");

        repository.deleteById(id);
    }
}
