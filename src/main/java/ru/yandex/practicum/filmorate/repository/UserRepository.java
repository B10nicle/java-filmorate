package ru.yandex.practicum.filmorate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.filmorate.entity.User;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface UserRepository extends JpaRepository<User, Long> {

/*    @Query("SELECT user FROM User user WHERE CONCAT(user.firstName, ' ', user.birthday, ' ', user.email, ' ', user.login) LIKE %?1%")
    List<User> search(String keyword);*/

    User findByEmail(String email);
}
