package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Service
@AllArgsConstructor
public class FilmService implements Services<Film> {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Override
    public void save(UserDto userDto) {
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

    public List<Film> getPopularFilms(int amount) {
        return filmRepository.findAll()
                .stream()
                .sorted(compareLikesSize().reversed())
                .limit(amount)
                .collect(Collectors.toList());
    }

    private static Comparator<Film> compareLikesSize() {
        return Comparator.comparingInt(film -> film.getLikes().size());
    }

    public int addLike(Long userId, Long filmId) {
        var user = userRepository.findById(userId);
        var film = filmRepository.findById(filmId);

        if (user.isEmpty() || film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующий объект (фильм/пользователь)");

        film.get().getLikes().add(user.get());
        return film.get().getLikes().size();
    }

    public int deleteLike(Long userId, Long filmId) {
        var user = userRepository.findById(userId);
        var film = filmRepository.findById(filmId);

        if (user.isEmpty() || film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующий объект (фильм/пользователь)");

        film.get().getLikes().remove(user.get());
        return film.get().getLikes().size();
    }
}
