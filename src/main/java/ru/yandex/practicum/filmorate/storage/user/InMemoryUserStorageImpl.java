package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Khilko
 */

@Component
public class InMemoryUserStorageImpl implements UserStorage {
    private final Map<Integer, User> users;
    private int id;

    public InMemoryUserStorageImpl() {
        this.users = new HashMap<>();
    }

    @Override
    public User addUser(User user) {
        user.setId(++id);
        return users.put(user.getId(), user);
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId()))
            return users.put(user.getId(), user);
        else
            throw new DoesntExistException("Пользователь: " + user.getId() + " " + user.getEmail() + " не сущестует");
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }
}
