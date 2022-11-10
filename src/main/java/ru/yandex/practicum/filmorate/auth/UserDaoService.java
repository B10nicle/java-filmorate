/*
package ru.yandex.practicum.filmorate.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.yandex.practicum.filmorate.entity.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.function.Predicate;
import java.util.Optional;

*/
/**
 * @author Oleg Khilko
 *//*


@Repository
public class UserDaoService implements UserDao {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserDaoService(PasswordEncoder passwordEncoder,
                          UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findAll().stream().filter(findUser(email)).findFirst();
    }

    private Predicate<User> findUser(String email) {
        return user -> email.equals(user.getEmail());
    }
}
*/
