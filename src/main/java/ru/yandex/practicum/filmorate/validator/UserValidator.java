package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author Oleg Khilko
 */

@Component
public class UserValidator implements Validator<User> {

    @Override
    public void validate(@NotNull User user) {
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
    }
}
