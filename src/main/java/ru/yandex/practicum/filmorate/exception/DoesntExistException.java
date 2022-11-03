package ru.yandex.practicum.filmorate.exception;

/**
 * @author Oleg Khilko
 */

public class DoesntExistException extends RuntimeException {
    public DoesntExistException(String message) {
        super(message);
    }
}
