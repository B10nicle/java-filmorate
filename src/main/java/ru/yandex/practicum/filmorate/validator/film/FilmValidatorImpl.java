package ru.yandex.practicum.filmorate.validator.film;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Oleg Khilko
 */

@Component
public class FilmValidatorImpl implements FilmValidator {

    @Override
    public void validate(@NotNull Film film) throws ValidationException {

        if (film.getName() == null
                || film.getName().isBlank())
            throw new ValidationException("Название не может быть пустым");

        if (film.getDescription() == null
                || film.getDescription().isBlank()
                || film.getDescription().length() > 200)
            throw new ValidationException("Максимальная длина описания — 200 символов");

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");

        if (film.getDuration() <= 0)
            throw new ValidationException("Продолжительность фильма должна быть положительной");
    }
}
