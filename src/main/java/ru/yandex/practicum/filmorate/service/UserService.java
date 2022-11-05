package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.AlreadyAddedException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.validator.Validator;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Service
public class UserService extends Services<User> {
    private final Validator<User> validator;
    private final Storage<User> storage;

    public UserService(Validator<User> validator,
                       Storage<User> storage) {
        this.validator = validator;
        this.storage = storage;
    }

    @Override
    public User add(User user) {
        boolean exists = storage.getAll().containsKey(user.getId());
        boolean isEmpty = storage.getAll().isEmpty();
        validator.validate(user);

        if (isEmpty)
            return storage.add(user);

        if (exists)
            throw new AlreadyAddedException(
                    "Пользователь: " + user.getId() + " "
                            + user.getEmail() + " уже добавлен");

        return storage.add(user);
    }

    @Override
    public User get(Long id) {
        boolean exists = storage.getAll().containsKey(id);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно получить несуществующего пользователя");

        return storage.getById(id);
    }

    @Override
    public void delete(Long id) {
        boolean exists = storage.getAll().containsKey(id);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующего пользователя");

        storage.deleteById(id);
    }

    @Override
    public List<User> search(String keyword) {
        return null;
    }
}