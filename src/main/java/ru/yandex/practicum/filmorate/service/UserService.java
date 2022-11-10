package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.yandex.practicum.filmorate.dto.UserRegistrationDto;
import ru.yandex.practicum.filmorate.entity.Role;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Oleg Khilko
 */

@Service
public class UserService implements Services<User>, UserDetailsService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       FilmRepository filmRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(UserRegistrationDto userRegistrationDto) {
        var user = new User(userRegistrationDto.getFirstName(),
                userRegistrationDto.getLastName(),
                passwordEncoder.encode(userRegistrationDto.getPassword()),
                userRegistrationDto.getEmail(),
                List.of(new Role("ROLE_USER")));

        return userRepository.save(user);
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
/*        if (keyword != null)
            return userRepository.search(keyword);*/

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
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
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

/*    public Set<User> addLike(Long userId, Long filmId) {
        var user = userRepository.findById(userId);
        var film = filmRepository.findById(filmId);

        if (user.isEmpty() || film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующий объект (фильм/пользователь)");

        film.get().getLikes().add(user.get());
        return film.get().getLikes();
    }

    public Set<User> deleteLike(Long userId, Long filmId) {
        var user = userRepository.findById(userId);
        var film = filmRepository.findById(filmId);

        if (user.isEmpty() || film.isEmpty())
            throw new DoesntExistException(
                    "Невозможно получить несуществующий объект (фильм/пользователь)");

        film.get().getLikes().remove(user.get());
        return film.get().getLikes();
    }*/
}
