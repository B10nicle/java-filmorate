package ru.yandex.practicum.filmorate.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.practicum.filmorate.entity.Film;

/**
 * @author Oleg Khilko
 */

public interface FilmRepository extends CrudRepository<Film, Long> {
}
