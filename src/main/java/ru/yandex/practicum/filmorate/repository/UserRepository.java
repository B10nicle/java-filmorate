package ru.yandex.practicum.filmorate.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.practicum.filmorate.entity.User;

/**
 * @author Oleg Khilko
 */

public interface UserRepository extends CrudRepository<User, Long> {
}
