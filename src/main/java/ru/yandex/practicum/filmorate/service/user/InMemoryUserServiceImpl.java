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
        userValidator.validate(user);

        if (userStorage.getUsers().get(user.getId()) != null)
            throw new AlreadyAddedException("Пользователь: " + user.getId() + " " + user.getEmail() + " уже добавлен");

        return userStorage.addUser(user);
    }

    @Override
    public User updateUser(User user) throws ValidationException {
        userValidator.validate(user);

        if (userStorage.getUsers().get(user.getId()) == null)
            throw new DoesntExistException("Пользователь: " + user.getId() + " " + user.getEmail() + " не сущестует");

        return userStorage.updateUser(user);
    }

    @Override
    public Map<Integer, User> getUsers() {
        return userStorage.getUsers();
    }
}
