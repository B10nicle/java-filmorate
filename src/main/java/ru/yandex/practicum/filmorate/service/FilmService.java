package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Oleg Khilko
 */

@Service
public class FilmService implements Services<Film> {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    public FilmService(FilmRepository filmRepository,
                       UserRepository userRepository) {
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Film add(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film getById(Long id) {
        var film = filmRepository.findById(id);

        if (film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующий фильм");

        return film.get();
    }

    @Override
    public List<Film> search(String keyword) {
        if (keyword != null)
            return filmRepository.search(keyword);

        return filmRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        var film = filmRepository.findById(id);

        if (film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно удалить несуществующий фильм");

        filmRepository.deleteById(id);
    }

    public void addLike(Long userId, Long filmId) {
        var user = userRepository.findById(userId);
        var film = filmRepository.findById(filmId);

        if (user.isEmpty() || film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующий объект (фильм/пользователь)");

        film.get().getLikes().add(user.get());
    }

    public void deleteLike(Long userId, Long filmId) {
        var user = userRepository.findById(userId);
        var film = filmRepository.findById(filmId);

        if (user.isEmpty() || film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующий объект (фильм/пользователь)");

        film.get().getLikes().remove(userId);
    }

    public List<Film> getPopularFilms(int amount) {
        return filmRepository.findAll()
                .stream()
                .sorted(Film::compareTo)
                .limit(amount)
                .collect(Collectors.toList());
    }
}
