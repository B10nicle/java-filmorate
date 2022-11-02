package ru.yandex.practicum.filmorate.validator;

/**
 * @author Oleg Khilko
 */

public interface Validator<T> {
    void validate(T t);
}
