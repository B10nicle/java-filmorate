package ru.yandex.practicum.filmorate.repository.mpa;

import ru.yandex.practicum.filmorate.entity.Mpa;

import java.util.Optional;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface MpaRepository {

    List<Mpa> getMpa();

    Optional<Mpa> getMpaById(int id);
}
