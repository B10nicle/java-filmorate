package ru.yandex.practicum.filmorate.controller;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.entity.Film;
import ru.yandex.practicum.filmorate.entity.User;
import ru.yandex.practicum.filmorate.entity.Mpa;
import org.junit.jupiter.api.BeforeEach;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
@AutoConfigureTestDatabase
class FilmControllerTest {

    @Autowired
    private FilmController controller;

    @Autowired
    private UserService service;

    private Film filmSample;
    private User user;

    @BeforeEach
    public void initialize() {
        filmSample = new Film(
                1,
                "New Film",
                "Film's description",
                LocalDate.of(2010, 11, 15),
                19,
                new Mpa(1, "G"));

        user = service.createUser(
                new User(
                        1,
                        "Anatoly16@gmail.com",
                        "Anatoly16",
                        "Anatoly",
                        LocalDate.of(2010, 11, 15)));
    }

    @Test
    void emptyNameExceptionTest() {
        filmSample.setName("");

        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createFilm(filmSample)
        );
        assertEquals("Name is blank", exception.getMessage());
    }

    @Test
    void nullExceptionTest() {
        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createFilm(null)
        );
        assertEquals("Film cannot be created", exception.getMessage());
    }

    @Test
    void negativeDurationExceptionTest() {
        filmSample.setDuration(-1);

        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createFilm(filmSample)
        );
        assertEquals("Film duration cannot be negative", exception.getMessage());
    }

    @Test
    void longDescriptionExceptionTest() {
        filmSample.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque auctor eu nisl " +
                "et tempus. Sed aliquam dignissim diam. Proin ut augue odio. Praesent non elementum dolor, a volutpat " +
                "dui. Ut mollis finibus odio, id iaculis sapien pharetra eget. Donec blandit vestibulum felis, in " +
                "condimentum risus condimentum a. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent " +
                "eu ante enim. Donec nec rhoncus exception. Quisque sit amet sapien risus. Donec facilisis elit non " +
                "elit laoreet lobortis. Aenean maximus exception vitae augue ornare lacinia. Sed sit amet magna a " +
                "tellus sagittis congue. Proin ac imperdiet felis, et blandit dolor. Mauris quis massa ornare enim " +
                "ornare aliquet id et tellus.\n" +
                "Aenean et varius lorem. Proin erat diam, dignissim quis gravida id, feugiat sollicitudin lectus. " +
                "Fusce a vulputate urna, ac faucibus lorem. Etiam rutrum, lectus vitae dapibus auctor, nisi lacus " +
                "vestibulum sapien, vel sodales tortor ipsum ac eros. Aliquam non arcu a ipsum lacinia luctus vitae " +
                "et arcu. Suspendisse tortor enim, tempus quis urna maximus, pulvinar semper urna. Cras luctus nibh " +
                "at mi venenatis dapibus. Vestibulum venenatis mi non risus porta, nec rhoncus arcu aliquam. " +
                "Sed posuere augue ac ipsum vestibulum laoreet.\n" +
                "Pellentesque a lorem ac enim imperdiet aliquet id sed massa. Vestibulum scelerisque fermentum lacus, " +
                "a pulvinar odio lobortis non. Nulla sagittis ullamcorper.");

        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createFilm(filmSample)
        );
        assertEquals("Max description size: 200", exception.getMessage());
    }

    @Test
    void releaseDayExceptionTest() {
        filmSample.setReleaseDate(LocalDate.of(1827, 12, 21));

        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createFilm(filmSample)
        );
        assertEquals("Movie cannot be before 28.12.1895", exception.getMessage());
    }

    @Test
    void addLikeFilmTest() {
        var film1 = controller.createFilm(filmSample);
        controller.addLike(film1.getId(), user.getId());
        var film2 = controller.getFilmById(film1.getId());

        assertTrue(film2.getLikes().contains(user.getId()));
        assertEquals(film2.getLikes().size(), 1);
    }

    @Test
    void incorrectIdExceptionTest() {
        filmSample.setId(-5);

        var exception = assertThrows(
                NotFoundException.class,
                () -> controller.updateFilm(filmSample)
        );
        assertEquals("Film doesn't exist", exception.getMessage());
    }

    @Test
    void notFoundUserExceptionTest() {
        var film = controller.createFilm(filmSample);

        var exception = assertThrows(
                NotFoundException.class,
                () -> controller.addLike(film.getId(), -5)
        );
        assertEquals("User doesn't exist", exception.getMessage());
    }

    @Test
    void zeroLikesTest() {
        var film1 = controller.createFilm(filmSample);
        var film2 = controller.getFilmById(film1.getId());

        assertNotNull(film2.getLikes());
        assertEquals(film2.getLikes().size(), 0);
    }

    @Test
    void popularFilmsTest() {
        var film1 = controller.createFilm(filmSample);
        var film2 = controller.createFilm(filmSample);

        controller.addLike(film1.getId(), user.getId());
        var films = controller.getPopularFilms(1);

        assertEquals(1, films.size());
        assertFalse(films.contains(film2));
    }

    @Test
    void notFoundFilmExceptionTest() {
        var exception = assertThrows(
                NotFoundException.class,
                () -> controller.addLike(-5, user.getId())
        );
        assertEquals("Film doesn't exist", exception.getMessage());
    }

    @Test
    void deleteLikeFilmExceptionTest() {
        var exception = assertThrows(
                NotFoundException.class,
                () -> controller.deleteLike(-5, user.getId())
        );
        assertEquals("Film doesn't exist", exception.getMessage());
    }

    @Test
    void deleteLikeFilmTest() {
        var film = controller.createFilm(filmSample);

        controller.addLike(film.getId(), user.getId());
        controller.deleteLike(film.getId(), user.getId());

        assertNotNull(film.getLikes());
        assertEquals(film.getLikes().size(), 0);
    }

    @Test
    void deleteLikeUserExceptionTest() {
        var film = controller.createFilm(filmSample);

        var exception = assertThrows(
                NotFoundException.class,
                () -> controller.deleteLike(film.getId(), -5)
        );
        assertEquals("User doesn't exist", exception.getMessage());
    }
}
