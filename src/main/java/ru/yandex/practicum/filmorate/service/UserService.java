package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.AlreadyAddedException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Service
public class UserService implements Services<User> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        validate(user);
        return userRepository.save(user);
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
    }

    @Override
    public User getById(Long id) {
        var user = userRepository.findById(id);

        if (user.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующего пользователя");

        return user.get();
    }

    @Override
    public List<User> search(String keyword) {
        if (keyword != null)
            return userRepository.search(keyword);

        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        var user = userRepository.findById(id);

        if (user.isEmpty())
            throw new DoesntExistException(
                    "Невозможно удалить несуществующего пользователя");

        userRepository.deleteById(id);
    }

    public List<User> getFriends(Long id) {
        var user = userRepository.findById(id);

        if (user.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующего пользователя");

        return new ArrayList<>(user.get().getFriends());
    }

    public void addFriend(Long myId, Long hisId) {
        var user = userRepository.findById(myId);
        var friend = userRepository.findById(hisId);

        if (user.isEmpty() || friend.isEmpty())
            throw new DoesntExistException(
                    "Данного пользователя не существует");

        user.get().getFriends().add(friend.get());
        friend.get().getFriends().add(user.get());
    }

    public void deleteFriend(Long myId, Long hisId) {
        var user = userRepository.findById(myId);
        var friend = userRepository.findById(hisId);

        if (user.isEmpty() || friend.isEmpty())
            throw new DoesntExistException(
                    "Данного пользователя не существует");

        user.get().getFriends().remove(friend.get());
        friend.get().getFriends().remove(user.get());
    }

    public List<User> getCommonFriends(Long myId, Long hisId) {
        var user = userRepository.findById(myId);
        var friend = userRepository.findById(hisId);

        if (user.isEmpty() || friend.isEmpty())
            throw new DoesntExistException(
                    "Данного пользователя не существует");

        var myFriends = user.get().getFriends();
        var hisFriends = friend.get().getFriends();

        return myFriends.stream()
                .filter(hisFriends::contains)
                .collect(Collectors.toList());
    }
}
