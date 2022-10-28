package ru.yandex.practicum.filmorate.exception;

/**
 * @author Oleg Khilko
 */

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
