package ru.yandex.practicum.filmorate.validatorTests;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import ru.yandex.practicum.filmorate.entity.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleg Khilko
 */

class FilmValidatorTest {
    private Validator<Film> validator;
    private Film film;

    @BeforeEach
    void filmInitialization() {
        validator = new FilmValidator();
        film = new Film();

        film.setId(0L);
        film.setDuration(123);
        film.setName("The Best Movie");
        film.setDescription("Yet another movie");
        film.setReleaseDate(LocalDate.of(2005, 3, 18));
    }

    @Test
    void filmValidationTest() {
        assertDoesNotThrow(() -> validator.validate(film));
    }

    @Test
    void releaseDateValidationTest() {
        film.setReleaseDate(LocalDate.of(1111, 1, 1));
        assertThrows(ValidationException.class, () -> validator.validate(film));
    }
}
