package ru.yandex.practicum.filmorate.entity;

import lombok.Data;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import java.time.LocalDate;

/**
 * @author Oleg Khilko
 */

@Data
public class User {
    private int id;

    private String name;

    @Email
    private String email;

    @NotBlank
    private String login;

    @PastOrPresent
    private LocalDate birthday;
}
