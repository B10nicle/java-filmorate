package ru.yandex.practicum.filmorate.validator.user;

import ru.yandex.practicum.filmorate.entity.User;

/**
 * @author Oleg Khilko
 */

public interface UserValidator {
    void validate(User user);
}
