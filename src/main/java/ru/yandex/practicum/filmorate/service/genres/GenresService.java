package ru.yandex.practicum.filmorate.service.genres;

import ru.yandex.practicum.filmorate.entity.Genre;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface GenresService {

    Genre getGenre(int id);

    List<Genre> getAllGenres();
}
