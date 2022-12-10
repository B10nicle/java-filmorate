package ru.yandex.practicum.filmorate.repository.genres;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.entity.Genre;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Component
@RequiredArgsConstructor
public class GenresRepositoryImpl implements GenresRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenres() {
        return jdbcTemplate.query("SELECT * FROM genre",
                (resultSet, rowNum) -> mapGenre(resultSet));
    }

    @Override
    public Optional<Genre> getGenreById(int id) {
        var genreRow = jdbcTemplate.queryForRowSet(
                "SELECT * FROM genre WHERE id = ?", id);
        if (genreRow.next()) {
            var genre = new Genre(
                    genreRow.getInt("id"),
                    genreRow.getString("name"));

            return Optional.of(genre);
        } else {
            return Optional.empty();
        }
    }

    private Genre mapGenre(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet.getInt("id"),
                resultSet.getString("name"));
    }
}
