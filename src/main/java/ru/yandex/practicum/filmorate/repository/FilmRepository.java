package ru.yandex.practicum.filmorate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.entity.Film;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("SELECT film FROM Film film WHERE CONCAT(film.id, ' ', film.name, ' ', film.description, ' ', film.duration, ' ', film.releaseDate) LIKE %?1%")
    List<Film> search(String keyword);
}
