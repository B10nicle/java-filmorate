package ru.yandex.practicum.filmorate.exception;

/**
 * @author Oleg Khilko
 */

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
