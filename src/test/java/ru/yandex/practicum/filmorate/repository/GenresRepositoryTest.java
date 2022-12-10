package ru.yandex.practicum.filmorate.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.yandex.practicum.filmorate.controller.FilmController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.entity.Film;
import ru.yandex.practicum.filmorate.entity.Mpa;
import org.junit.jupiter.api.BeforeEach;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.repository.film.FilmRepositoryImpl;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Oleg Khilko
 */

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class GenresRepositoryTest {

    @Autowired
    private final FilmRepositoryImpl repository;

    @Autowired
    private final FilmController controller;

    private Film filmSample;

    @BeforeEach
    public void initialize() {
        filmSample = controller.createFilm(
                new Film(
                        1,
                        "New Film",
                        "Film's description",
                        LocalDate.of(2010, 11, 15),
                        19,
                        new Mpa(1, "G")));
    }

    @Test
    void addGenreTest() {
        assertThatNoException().isThrownBy(
                () -> repository.addGenres(filmSample.getId(), List.of(1)));
    }

    @Test
    void removeGenreTest() {
        repository.addGenres(filmSample.getId(), List.of(1));
        assertThatNoException().isThrownBy(
                () -> repository.removeGenres(filmSample.getId(), List.of(1)));
    }

    @Test
    void getGenresByIdsTest() {
        var genres = repository.getGenresByIds(List.of(2, 3));

        assertEquals(genres.get(2).getName(), "Драма");
        assertEquals(2, genres.size());
        assertFalse(genres.isEmpty());
    }
}
