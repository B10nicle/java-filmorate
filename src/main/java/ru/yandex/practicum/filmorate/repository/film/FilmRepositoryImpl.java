package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.repository.genres.GenresRepositoryImpl;
import ru.yandex.practicum.filmorate.entity.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.entity.Film;
import ru.yandex.practicum.filmorate.entity.Mpa;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.Objects;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Component
@RequiredArgsConstructor
public class FilmRepositoryImpl implements FilmRepository {
    private final GenresRepositoryImpl repository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film createFilm(Film film) {
        jdbcTemplate.update("INSERT INTO film (id, name, description, release_date, duration, mpa) " +
                        "VALUES(?,?,?,?,?,?)",
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());

        if (film.getGenres() != null) {
            var genreIds = film.getGenres()
                    .stream()
                    .map(Genre::getId)
                    .collect(Collectors.toList());

            repository.addGenres(film.getId(), genreIds);
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update(
                "UPDATE film " +
                        "SET name = ?, description = ?, release_date = ?, duration = ?, mpa = ?" +
                        "WHERE id = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        var filmGenres = repository.getFilmGenres(film.getId());

        var filmsAdded = film.getGenres().stream()
                .filter(genre -> !filmGenres.contains(genre))
                .map(Genre::getId)
                .collect(Collectors.toList());
        repository.addGenres(film.getId(), filmsAdded);

        var filmsRemoved = filmGenres.stream()
                .filter(genre -> !film.getGenres().contains(genre))
                .map(Genre::getId)
                .collect(Collectors.toList());
        repository.removeGenres(film.getId(), filmsRemoved);

        return film;
    }

    @Override
    public List<Film> getFilms() {
        return jdbcTemplate.query(
                "SELECT f.*, m.name AS mpa_name, m.id AS mpa_id " +
                        "FROM film AS f " +
                        "INNER JOIN mpa AS m ON f.mpa = m.id", (resultSet, rowNum) -> mapFilm(resultSet));
    }

    @Override
    public Optional<Film> getFilmById(int filmId) {
        var filmRow = jdbcTemplate.queryForRowSet(
                "SELECT f.*, m.name AS mpa_name " +
                        "FROM film AS f " +
                        "INNER JOIN mpa AS m ON f.mpa = m.id " +
                        "AND f.id = ?", filmId);

        if (filmRow.next()) {
            var film = Film.builder()
                    .releaseDate(Objects.requireNonNull(filmRow.getDate("release_date")).toLocalDate())
                    .mpa(new Mpa(filmRow.getInt("mpa"), filmRow.getString("MPA_NAME")))
                    .name(Objects.requireNonNull(filmRow.getString("name")))
                    .description(filmRow.getString("description"))
                    .duration(filmRow.getInt("duration"))
                    .id(filmRow.getInt("id"))
                    .build();

            film.getGenres().addAll(repository.getFilmGenres(filmId));
            film.getLikes().addAll(getUserLikes(filmId));
            return Optional.of(film);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void addLike(Film film, int userId) {
        jdbcTemplate.update("INSERT INTO film_likes (user_id, film_id) " +
                "VALUES(?,?)", userId, film.getId());
    }

    @Override
    public void deleteLike(Film film, int userId) {
        jdbcTemplate.update("DELETE " +
                "FROM film_likes " +
                "WHERE user_id = ? AND film_id = ?", userId, film.getId());
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        var films = jdbcTemplate.query(
                "SELECT f.*, m.id AS mpa_id, m.name AS mpa_name " +
                        "FROM film AS f " +
                        "INNER JOIN mpa AS m ON f.mpa = m.id " +
                        "LEFT OUTER JOIN film_likes AS fl ON f.id = fl.film_id " +
                        "GROUP BY f.id, fl.user_id " +
                        "ORDER BY COUNT(fl.user_id) DESC " +
                        "LIMIT ?", (resultSet, rowNum) -> mapFilm(resultSet), count);

        films.forEach(film -> {
            film.getGenres().addAll(repository.getFilmGenres(film.getId()));
            film.getLikes().addAll(getUserLikes(film.getId()));
        });
        return films;
    }

    private Film mapFilm(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");

        var film = Film.builder()
                .id(id)
                .mpa(new Mpa(resultSet.getInt("mpa_id"), resultSet.getString("mpa_name")))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .description(resultSet.getString("description"))
                .duration(resultSet.getInt("duration"))
                .name(resultSet.getString("name"))
                .build();

        film.getGenres().addAll(repository.getFilmGenres(id));
        film.getLikes().addAll(getUserLikes(id));

        return film;
    }

    private List<Integer> getUserLikes(int filmId) {
        return jdbcTemplate.query("SELECT user_id " +
                "FROM film_likes " +
                "WHERE film_id = ?", (resultSet, rowNum) ->
                resultSet.getInt("user_id"), filmId);
    }
}
