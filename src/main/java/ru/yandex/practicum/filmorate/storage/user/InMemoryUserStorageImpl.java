package ru.yandex.practicum.filmorate.storage.user;

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
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public User getUser(int userId) {
        return users.get(userId);
    }

    @Override
    public void deleteUsers() {
        users.clear();
    }

    @Override
    public void deleteUser(int userId) {
        users.remove(userId);
    }
}
