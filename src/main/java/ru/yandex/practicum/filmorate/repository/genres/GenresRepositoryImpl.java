package ru.yandex.practicum.filmorate.repository.genres;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.entity.Genre;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<Integer, Genre> getGenresByIds(List<Integer> ids) {
        return jdbcTemplate.query(String.format(
                                "SELECT * FROM genre WHERE id IN (%s)",
                                String.join(",", Collections.nCopies(ids.size(), "?"))),
                        (resultSet, rowNum) -> mapGenre(resultSet), ids.toArray())
                .stream()
                .collect(Collectors.toMap(Genre::getId, Function.identity()));
    }

    @Override
    public void addGenres(int filmId, List<Integer> genres) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO film_genre (film_id, genre_id) VALUES (?,?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement preparedStatement, int value)
                            throws SQLException {
                        preparedStatement.setInt(1, filmId);
                        preparedStatement.setInt(2, genres.get(value));
                    }

                    public int getBatchSize() {
                        return genres.size();
                    }
                });
    }

    @Override
    public void removeGenres(int filmId, List<Integer> genres) {
        jdbcTemplate.batchUpdate(
                "DELETE FROM film_genre WHERE film_id = ? AND genre_id = ?",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int value)
                            throws SQLException {
                        ps.setInt(1, filmId);
                        ps.setInt(2, genres.get(value));
                    }

                    public int getBatchSize() {
                        return genres.size();
                    }
                });
    }

    @Override
    public List<Genre> getFilmGenres(int filmId) {
        return jdbcTemplate.query(
                "SELECT * " +
                        "FROM genre " +
                        "INNER JOIN film_genre ON film_genre.genre_id = genre.id " +
                        "AND film_genre.film_id = ?",
                (resultSet, rowNum) -> mapGenre(resultSet), filmId);
    }

    private Genre mapGenre(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet.getInt("id"),
                resultSet.getString("name"));
    }
}
