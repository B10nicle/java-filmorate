package ru.yandex.practicum.filmorate.validatorTests;

import ru.yandex.practicum.filmorate.validator.user.UserValidatorImpl;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validator.user.UserValidator;
import ru.yandex.practicum.filmorate.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleg Khilko
 */

class UserValidatorImplTest {
    private UserValidator userValidator;
    private User user;

    @BeforeEach
    void userInitialization() {
        userValidator = new UserValidatorImpl();
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
        assertThrows(ValidationException.class, () -> userValidator.validate(user));
    }

    @Test
    void userValidationTest() {
        assertDoesNotThrow(() -> userValidator.validate(user));
    }

    @Test
    void emailWithoutAtValidationTest() {
        user.setEmail("Mr.Gold");
        assertThrows(ValidationException.class, () -> userValidator.validate(user));
    }

    @Test
    void nullNameValidationTest() {
        user.setName(null);
        userValidator.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    void nullLoginValidationTest() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> userValidator.validate(user));
    }

    @Test
    void nullEmailValidationTest() {
        user.setEmail(null);
        assertThrows(ValidationException.class, () -> userValidator.validate(user));
    }

    @Test
    void spaceLoginValidationTest() {
        user.setLogin(" ");
        assertThrows(ValidationException.class, () -> userValidator.validate(user));
    }

    @Test
    void spaceNameValidationTest() throws ValidationException {
        user.setName(" ");
        userValidator.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }
}
