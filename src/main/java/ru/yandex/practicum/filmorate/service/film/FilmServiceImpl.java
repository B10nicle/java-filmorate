package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.repository.genres.GenresRepository;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import ru.yandex.practicum.filmorate.exception.DoesntFindException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.repository.mpa.MpaRepository;
import ru.yandex.practicum.filmorate.entity.Genre;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final GenresRepository genresRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final MpaRepository mpaRepository;
    private int idGenerator;

    @Override
    public Film createFilm(Film film) {
        if (film == null) throw new ValidationException("Film cannot be created");
        validateFilm(film);
        var mpa = mpaRepository.getMpaById(film.getMpa().getId())
                .orElseThrow(() -> {
                    throw new DoesntFindException("MPA doesn't exist");
                });
        film.setId(generateId());
        addFilmGenres(film);
        film.setMpa(mpa);

        return filmRepository.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        if (film == null) throw new ValidationException("Film cannot be updated");
        if (filmRepository.getFilmById(film.getId()).isEmpty()) throw new DoesntFindException("Film doesn't exist");
        validateFilm(film);
        var mpa = mpaRepository.getMpaById(film.getMpa().getId())
                .orElseThrow(() -> {
                    throw new DoesntFindException("MPA doesn't exist");
                });
        addFilmGenres(film);
        film.setMpa(mpa);

        return filmRepository.updateFilm(film);
    }

    @Override
    public List<Film> getFilms() {
        return filmRepository.getFilms();
    }

    @Override
    public Film getFilmById(int id) {
        return filmRepository.getFilmById(id).orElseThrow(() -> {
            throw new DoesntFindException("Film doesn't exist");
        });
    }

    @Override
    public void addLike(int userId, int filmId) {
        userRepository.getUserById(userId).orElseThrow(() -> {
            throw new DoesntFindException("User doesn't exist");
        });

        var film = filmRepository.getFilmById(filmId).orElseThrow(() -> {
            throw new DoesntFindException("Film doesn't exist");
        });
        filmRepository.addLike(film, userId);
    }

    @Override
    public void deleteLike(int userId, int filmId) {
        userRepository.getUserById(userId).orElseThrow(() -> {
            throw new DoesntFindException("User doesn't exist");
        });
        var film = filmRepository.getFilmById(filmId).orElseThrow(() -> {
            throw new DoesntFindException("Film doesn't exist");
        });
        filmRepository.deleteLike(film, userId);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count);
    }

    private int generateId() {
        return ++idGenerator;
    }

    private void addFilmGenres(Film film) {
        if (film.getGenres() != null) {
            var genresIds = film.getGenres()
                    .stream()
                    .map(Genre::getId)
                    .distinct()
                    .collect(Collectors.toList());
            var genres = genresRepository.getGenresByIds(genresIds);
            if (genres.size() != genresIds.size()) {
                throw new DoesntFindException("Genre doesn't exist");
            }
            film.getGenres().clear();
            film.getGenres().addAll(genres.values());
        }
    }

    private void validateFilm(Film film) {
        if (film.getName().isBlank()) throw new ValidationException("Name is blank");
        if (film.getDescription().length() > 200) throw new ValidationException("Max description size: 200");
        if (film.getDuration() < 0) throw new ValidationException("Film duration cannot be negative");
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ValidationException("Movie cannot be before 28.12.1895");
        }
    }
}
