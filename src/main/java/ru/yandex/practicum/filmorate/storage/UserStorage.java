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
    private final Map<Long, User> users;
    private Long id;

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
    public Map<Long, User> getAll() {
        return users;
    }

    @Override
    public User getById(Long id) {
        return users.get(id);
    }

    @Override
    public void deleteById(Long id) {
        users.remove(id);
    }
}
