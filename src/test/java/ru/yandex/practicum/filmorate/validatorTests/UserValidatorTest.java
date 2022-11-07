package ru.yandex.practicum.filmorate.validatorTests;

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

        user.setId(0L);
        user.setName("James Gold");
        user.setLogin("JamesGold009");
        user.setEmail("jamesgold@gmail.ru");
        user.setBirthday(LocalDate.of(1969, 7, 13));
    }

    @Test
    void userValidationTest() {
        assertDoesNotThrow(() -> validator.validate(user));
    }
}
