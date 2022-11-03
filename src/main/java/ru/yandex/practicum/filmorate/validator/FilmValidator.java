package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Oleg Khilko
 */

@Component
public class FilmValidator implements Validator<Film> {

    @Override
    public void validate(@NotNull Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
    }
}
