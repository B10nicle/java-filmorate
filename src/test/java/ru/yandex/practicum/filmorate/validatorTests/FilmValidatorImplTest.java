package ru.yandex.practicum.filmorate.validatorTests;

import ru.yandex.practicum.filmorate.validator.film.FilmValidatorImpl;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validator.film.FilmValidator;
import ru.yandex.practicum.filmorate.entity.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleg Khilko
 */

class FilmValidatorImplTest {
    private FilmValidator filmValidator;
    private Film film;

    @BeforeEach
    void filmInitialization() {
        filmValidator = new FilmValidatorImpl();
        film = new Film();

        film.setId(0);
        film.setDuration(123);
        film.setName("The Best Movie");
        film.setDescription("Yet another movie");
        film.setReleaseDate(LocalDate.of(2005, 3, 18));
    }

    @Test
    void nullNameValidationTest() {
        film.setName(null);
        assertThrows(ValidationException.class, () -> filmValidator.validate(film));
    }

    @Test
    void moreThan200symbolsDescriptionValidationTest() {
        film.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem " +
                "Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer " +
                "took a galley of type and scrambled it to make a type specimen book. It has survived not only " +
                "five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
                "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum " +
                "passages, and more recently with desktop publishing software like Aldus PageMaker including " +
                "versions of Lorem Ipsum.");
        assertThrows(ValidationException.class, () -> filmValidator.validate(film));
    }

    @Test
    void spaceNameValidationTest() {
        film.setName(" ");
        assertThrows(ValidationException.class, () -> filmValidator.validate(film));
    }

    @Test
    void filmDurationValidationTest() {
        film.setDuration(-1);
        assertThrows(ValidationException.class, () -> filmValidator.validate(film));
    }

    @Test
    void releaseDateValidationTest() {
        film.setReleaseDate(LocalDate.of(1111, 1, 1));
        assertThrows(ValidationException.class, () -> filmValidator.validate(film));
    }

    @Test
    void nullDescriptionValidationTest() {
        film.setDescription(null);
        assertThrows(ValidationException.class, () -> filmValidator.validate(film));
    }

    @Test
    void filmValidationTest() {
        assertDoesNotThrow(() -> filmValidator.validate(film));
    }

    @Test
    void spaceDescriptionValidationTest() {
        film.setDescription(" ");
        assertThrows(ValidationException.class, () -> filmValidator.validate(film));
    }
}
