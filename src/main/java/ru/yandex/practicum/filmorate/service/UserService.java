package ru.yandex.practicum.filmorate.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import org.springframework.security.core.userdetails.UserDetails;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import ru.yandex.practicum.filmorate.dto.UserMapper;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.entity.Role;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * @author Oleg Khilko
 */

@Service
@AllArgsConstructor
public class UserService implements Services<User>, UserDetailsService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final UserMapper userMapper;

    @Override
    public void save(UserDto userDto) {
        userRepository.save(userMapper.mapToEntity(userDto));
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);

        if (user == null)
            throw new UsernameNotFoundException(String.format("Username %s not found", username));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Set.of(mapRoleToAuthority(user.getRole())));
    }

    private GrantedAuthority mapRoleToAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getName());
    }

/*    public List<User> getFriends(Long id) {
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
    }*/
}
