package ru.yandex.practicum.filmorate.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author Oleg Khilko
 */

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Email
    private String email;

    @NotBlank
    private String login;

    @PastOrPresent
    private LocalDate birthday;
}
