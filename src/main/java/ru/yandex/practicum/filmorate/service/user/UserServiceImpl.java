package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private int idGenerator;

    @Override
    public User createUser(User user) {
        if (user == null) throw new ValidationException("User cannot be created");
        validateUser(user);
        user.setId(generatedId());

        return repository.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        if (user == null) throw new ValidationException("User cannot be updated");
        if (repository.getUserById(user.getId()).isEmpty()) throw new NotFoundException("User does not exist");
        validateUser(user);

        return repository.updateUser(user).get();
    }

    @Override
    public List<User> getUsers() {
        return repository.getUsers();
    }

    @Override
    public User getUserById(int userId) {
        return repository.getUserById(userId).orElseThrow(() -> {
            throw new NotFoundException("User does not exist");
        });
    }

    @Override
    public void addFriend(int userId, int friendId) {
        var user = getUserById(userId);
        var friend = getUserById(friendId);
        repository.addFriend(user, friend);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        var user = getUserById(userId);
        var friend = getUserById(friendId);
        repository.removeFriend(user, friend);
    }

    @Override
    public List<User> getFriends(int userId) {
        var user = getUserById(userId);
        return user.getFriends().stream()
                .map(repository::getUserById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(int userId, int friendId) {
        var user = getUserById(userId);
        var friend = getUserById(friendId);
        return   user.getFriends().stream().filter(id -> friend.getFriends().contains(id))
                .map(repository::getUserById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private int generatedId() {
        return ++idGenerator;
    }

    private void validateUser(User user) {
        if (!user.getEmail().contains("@") || user.getEmail().isBlank()) throw new ValidationException("Incorrect email");
        if (user.getBirthday().isAfter(LocalDate.now())) throw new ValidationException("Incorrect DOB");
        if (user.getLogin().contains(" ")) throw new ValidationException("Incorrect login");
        if (user.getLogin().isBlank()) throw new ValidationException("Login is blank");
        if (user.getName().isBlank()) user.setName(user.getLogin());
    }
}
