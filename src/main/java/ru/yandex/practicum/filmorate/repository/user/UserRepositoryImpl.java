package ru.yandex.practicum.filmorate.repository.user;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.Objects;
import java.util.List;
import java.sql.Date;

/**
 * @author Oleg Khilko
 */

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT * FROM users",
                (resultSet, rowNum) -> mapUser(resultSet));
    }

    @Override
    public Optional<User> getUserById(int id) {
        var userRow = jdbcTemplate.queryForRowSet(
                "SELECT * FROM users WHERE id = ?", id);

        if (userRow.next()) {
            var user = User.builder()
                    .birthday(Objects.requireNonNull(userRow.getDate("birthday")).toLocalDate())
                    .email(Objects.requireNonNull(userRow.getString("email")))
                    .login(Objects.requireNonNull(userRow.getString("login")))
                    .name(userRow.getString("name"))
                    .id(userRow.getInt("id"))
                    .build();

            user.getFriends().addAll(getFriends(id));
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public User createUser(User user) {
        jdbcTemplate.update(
                "INSERT INTO users (ID, EMAIL, LOGIN, NAME, BIRTHDAY) VALUES (?,?,?,?,?)",
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()));

        return user;
    }

    @Override
    public Optional<User> updateUser(User user) {
        jdbcTemplate.update(
                "DELETE " +
                        "FROM users " +
                        "WHERE id = ?",
                user.getId());
        jdbcTemplate.update(
                "INSERT INTO users (id, email, login, name, birthday) " +
                        "VALUES (?,?,?,?,?)",
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());

        return getUserById(user.getId());
    }

    @Override
    public void addFriend(User user, User friend) {
        jdbcTemplate.update(
                "INSERT INTO user_friends (user_id, friends_id) " +
                        "VALUES (?,?)",
                user.getId(), friend.getId());
    }

    @Override
    public void removeFriend(User user, User friend) {
        jdbcTemplate.update(
                "DELETE FROM user_friends " +
                        "WHERE user_id = ? AND friends_id = ?",
                user.getId(),
                friend.getId());
    }

    private List<Integer> getFriends(int userId) {
        return jdbcTemplate.query(
                "SELECT friends_id " +
                        "FROM user_friends " +
                        "WHERE user_id = ?",
                (resultSet, rowNum) -> resultSet.getInt("friends_id"), userId);
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");

        var user = User.builder()
                .id(id)
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .login(resultSet.getString("login"))
                .email(resultSet.getString("email"))
                .name(resultSet.getString("name"))
                .build();

        user.getFriends().addAll(getFriends(id));
        return user;
    }
}
