package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.AlreadyAddedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Oleg Khilko
 */

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        log.error("error: " + e.getMessage());
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleDoesntExistException(final DoesntExistException e) {
        log.error("error: " + e.getMessage());
        return Map.of("error", "Данного фильма не существует",
                "errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleAlreadyAddedException(final AlreadyAddedException e) {
        log.error("error: " + e.getMessage());
        return Map.of("error", "Данный фильм уже добавлен",
                "errorMessage", e.getMessage());
    }
}
