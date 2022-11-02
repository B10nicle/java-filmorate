package ru.yandex.practicum.filmorate.validatorTests;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validator.UserValidator;
import ru.yandex.practicum.filmorate.validator.Validator;
import ru.yandex.practicum.filmorate.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleg Khilko
 */

class UserValidatorTest {
    private Validator<User> validator;
    private User user;

    @BeforeEach
    void userInitialization() {
        validator = new UserValidator();
        user = new User();

        user.setId(0);
        user.setName("James Gold");
        user.setLogin("JamesGold009");
        user.setEmail("jamesgold@gmail.ru");
        user.setBirthday(LocalDate.of(1969, 7, 13));
    }

    @Test
    void futureBirthdayValidationTest() {
        user.setBirthday(LocalDate.of(2042, 3, 11));
        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    void userValidationTest() {
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @Test
    void emailWithoutAtValidationTest() {
        user.setEmail("Mr.Gold");
        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    void nullNameValidationTest() {
        user.setName(null);
        validator.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    void nullLoginValidationTest() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    void nullEmailValidationTest() {
        user.setEmail(null);
        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    void spaceLoginValidationTest() {
        user.setLogin(" ");
        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    void spaceNameValidationTest() throws ValidationException {
        user.setName(" ");
        validator.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }
}
