package ru.yandex.practicum.filmorate.exception;

/**
 * @author Oleg Khilko
 */

public class DoesntFindException extends RuntimeException {
    public DoesntFindException(String message) {
        super(message);
    }
}
