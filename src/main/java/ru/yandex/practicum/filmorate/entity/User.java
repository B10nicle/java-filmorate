package ru.yandex.practicum.filmorate.entity;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Oleg Khilko
 */

@Data
public class User {
    private Long id;

    private String name;

    @Email
    private String email;

    @NotBlank
    private String login;

    @PastOrPresent
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();
}
