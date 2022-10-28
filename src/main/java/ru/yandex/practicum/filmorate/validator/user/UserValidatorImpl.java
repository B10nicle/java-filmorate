package ru.yandex.practicum.filmorate.validator.user;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Oleg Khilko
 */

@Component
public class UserValidatorImpl implements UserValidator {

    @Override
    public void validate(@NotNull User user) throws ValidationException {

        if (user.getEmail() == null
                || !user.getEmail().contains("@"))
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");

        if (user.getLogin() == null
                || user.getLogin().isBlank())
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");

        if (user.getName() == null
                || user.getName().isBlank())
            user.setName(user.getLogin());

        if (user.getBirthday().isAfter(LocalDate.now()))
            throw new ValidationException("Дата рождения не может быть в будущем");
    }
}