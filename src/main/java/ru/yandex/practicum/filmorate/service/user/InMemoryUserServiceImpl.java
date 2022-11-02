package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorageImpl;
import ru.yandex.practicum.filmorate.exception.AlreadyAddedException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validator.user.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Oleg Khilko
 */

@Service
public class InMemoryUserServiceImpl implements UserService {
    private final InMemoryUserStorageImpl userStorage;
    private final UserValidator userValidator;

    @Autowired
    public InMemoryUserServiceImpl(InMemoryUserStorageImpl userStorage,
                                   UserValidator userValidator) {
        this.userValidator = userValidator;
        this.userStorage = userStorage;
    }

    @Override
    public User addUser(User user) throws ValidationException {
        boolean exists = userStorage.getUsers().containsKey(user.getId());
        boolean isEmpty = userStorage.getUsers().isEmpty();
        userValidator.validate(user);

        if (isEmpty)
            return userStorage.addUser(user);

        if (exists)
            throw new AlreadyAddedException(
                    "Пользователь: " + user.getId() + " "
                            + user.getEmail() + " уже добавлен");

        return userStorage.addUser(user);
    }

    @Override
    public User updateUser(User user) throws ValidationException {
        boolean exists = userStorage.getUsers().containsKey(user.getId());
        userValidator.validate(user);

        if (!exists)
            throw new DoesntExistException(
                    "Пользователь: " + user.getId() + " "
                            + user.getEmail() + " не сущестует");

        return userStorage.updateUser(user);
    }

    @Override
    public Map<Integer, User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    public User getUser(int userId) {
        boolean exists = userStorage.getUsers().containsKey(userId);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно получить несуществующего пользователя");

        return userStorage.getUser(userId);
    }

    @Override
    public void deleteUsers() {
        boolean isEmpty = userStorage.getUsers().isEmpty();

        if (isEmpty)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующих пользователей");

        userStorage.deleteUsers();
    }

    @Override
    public void deleteUser(int userId) {
        boolean exists = userStorage.getUsers().containsKey(userId);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующего пользователя");

        userStorage.deleteUser(userId);
    }
}
