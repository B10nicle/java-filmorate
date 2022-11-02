package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Khilko
 */

@Component
public class UserStorage extends Storage<User> {
    private final Map<Integer, User> users;
    private int id;

    public UserStorage() {
        this.users = new HashMap<>();
    }

    @Override
    public User add(User user) {
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Map<Integer, User> getAll() {
        return users;
    }

    @Override
    public User getById(int id) {
        return users.get(id);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public void deleteById(int id) {
        users.remove(id);
    }
}
