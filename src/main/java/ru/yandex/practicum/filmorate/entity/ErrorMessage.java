package ru.yandex.practicum.filmorate.entity;

/**
 * @author Oleg Khilko
 */

public class ErrorMessage {

    private final String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
