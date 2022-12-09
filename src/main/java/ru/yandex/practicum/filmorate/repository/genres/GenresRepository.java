package ru.yandex.practicum.filmorate.repository.genres;

import ru.yandex.practicum.filmorate.entity.Genre;

import java.util.Optional;
import java.util.List;
import java.util.Map;

/**
 * @author Oleg Khilko
 */

public interface GenresRepository {

    List<Genre> getGenres();

    Optional<Genre> getGenreById(int id);

    Map<Integer, Genre> getGenresByIds(List<Integer> ids);

    void addGenres(int filmId, List<Integer> genres);

    void removeGenres(int filmId, List<Integer> genres);

    List<Genre> getFilmGenres(int filmId);
}
