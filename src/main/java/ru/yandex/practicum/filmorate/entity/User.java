package ru.yandex.practicum.filmorate.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Oleg Khilko
 */

@Data
public class User {
    private LocalDate birthday;
    private String email;
    private String login;
    private String name;
    private int id;
}
