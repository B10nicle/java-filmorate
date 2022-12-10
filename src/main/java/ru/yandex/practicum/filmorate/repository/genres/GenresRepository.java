package ru.yandex.practicum.filmorate.repository.genres;

import ru.yandex.practicum.filmorate.entity.Genre;

import java.util.Optional;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface GenresRepository {

    List<Genre> getGenres();

    Optional<Genre> getGenreById(int id);
}
