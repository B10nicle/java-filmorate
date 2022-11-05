package ru.yandex.practicum.filmorate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.entity.User;

/**
 * @author Oleg Khilko
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
