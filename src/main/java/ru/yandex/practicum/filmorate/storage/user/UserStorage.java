package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.entity.User;

import java.util.Map;

/**
 * @author Oleg Khilko
 */

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    Map<Integer, User> getUsers();

    void deleteUsers();

    void deleteUser(int userId);
}
