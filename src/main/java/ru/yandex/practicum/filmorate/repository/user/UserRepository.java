package ru.yandex.practicum.filmorate.repository.user;

import ru.yandex.practicum.filmorate.entity.User;

import java.util.Optional;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface UserRepository {

    User createUser(User user);

    Optional<User> updateUser(User user);

    List<User> getUsers();

    Optional<User> getUserById(int id);

    void addFriend(User user, User friend);

    void removeFriend(User user, User friend);
}
