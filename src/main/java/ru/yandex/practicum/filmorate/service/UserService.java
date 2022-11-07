package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.AlreadyAddedException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.validator.Validator;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Service
public class UserService extends ServiceAbs<User> {
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
        boolean storageIsEmpty = storage.getAll().isEmpty();
        validator.validate(user);

        if (storageIsEmpty)
            return storage.add(user);

        if (exists)
            throw new AlreadyAddedException(
                    "Пользователь: " + user.getId() + " "
                            + user.getEmail() + " уже добавлен");

        return storage.add(user);
    }

    @Override
    public User update(User user) {
        boolean exists = storage.getAll().containsKey(user.getId());
        validator.validate(user);

        if (!exists)
            throw new DoesntExistException(
                    "Пользователь: " + user.getId() + " "
                            + user.getEmail() + " не сущестует");

        return storage.update(user);
    }

    @Override
    public User getById(Long id) {
        boolean exists = storage.getAll().containsKey(id);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно получить несуществующего пользователя");

        return storage.getById(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(storage.getAll().values());
    }

    @Override
    public void deleteAll() {
        boolean storageIsEmpty = storage.getAll().isEmpty();

        if (storageIsEmpty)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующих пользователей");

        storage.deleteAll();
    }

    @Override
    public void delete(Long id) {
        boolean exists = storage.getAll().containsKey(id);

        if (!exists)
            throw new DoesntExistException(
                    "Невозможно удалить несуществующего пользователя");

        storage.deleteById(id);
    }

    public List<User> getFriends(Long id) {
        return storage.getById(id)
                .getFriends()
                .stream()
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public void addFriend(Long myId, Long hisId) {
        boolean userDoesntExist = storage.getAll().get(myId) == null;
        boolean friendDoesntExist = storage.getAll().get(hisId) == null;

        if (userDoesntExist || friendDoesntExist)
            throw new DoesntExistException(
                    "Данного пользователя не существует");

        storage.getById(myId).getFriends().add(hisId);
        storage.getById(hisId).getFriends().add(myId);
    }

    public void deleteFriend(Long myId, Long hisId) {
        boolean userDoesntExist = storage.getAll().get(myId) == null;
        boolean friendDoesntExist = storage.getAll().get(hisId) == null;

        if (userDoesntExist || friendDoesntExist)
            throw new DoesntExistException(
                    "Данного пользователя не существует");

        storage.getById(myId).getFriends().remove(hisId);
        storage.getById(hisId).getFriends().remove(myId);
    }

    public List<User> getCommonFriends(Long myId, Long hisId) {
        var myFriends = storage.getAll().get(myId).getFriends();
        var hisFriends = storage.getAll().get(hisId).getFriends();

        return myFriends.stream()
                .filter(hisFriends::contains)
                .map(this::getById)
                .collect(Collectors.toList());
    }
}
