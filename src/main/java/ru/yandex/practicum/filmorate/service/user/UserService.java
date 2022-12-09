package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.entity.User;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface UserService {

    User createUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    User getUserById(int id);

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    List<User> getFriends(int userId);

    List<User> getCommonFriends(int userId, int friendId);
}
