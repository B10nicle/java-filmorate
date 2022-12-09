package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.entity.Mpa;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface MpaService {

    Mpa getMpa(int id);

    List<Mpa> getAllMpa();
}
