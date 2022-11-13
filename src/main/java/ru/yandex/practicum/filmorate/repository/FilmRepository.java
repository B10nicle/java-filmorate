package ru.yandex.practicum.filmorate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.filmorate.entity.Film;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("SELECT film FROM Film film WHERE CONCAT(film.name, ' ', " +
            "film.description, ' ', film.duration, ' ', film.releaseDate) LIKE %?1%")

    List<Film> search(String keyword);
}
