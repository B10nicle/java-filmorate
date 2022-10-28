package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.entity.User;

import java.util.Map;

/**
 * @author Oleg Khilko
 */

public interface UserService {
    User addUser(User user);

    User updateUser(User user);

    Map<Integer, User> getUsers();
}
