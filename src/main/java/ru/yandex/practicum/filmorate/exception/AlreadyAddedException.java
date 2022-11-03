package ru.yandex.practicum.filmorate.exception;

/**
 * @author Oleg Khilko
 */

public class AlreadyAddedException extends RuntimeException {
    public AlreadyAddedException(String message) {
        super(message);
    }
}
