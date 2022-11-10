package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.UserRegistrationDto;
import ru.yandex.practicum.filmorate.entity.User;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface Services<T> {

    T add(T t);

    T getById(Long id);

    void delete(Long id);

    List<T> search(String keyword);

    User save(UserRegistrationDto userRegistrationDto);
}
