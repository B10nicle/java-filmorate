package ru.yandex.practicum.filmorate.entity;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * @author Oleg Khilko
 */

@Data
public class Film {
    private int id;

    @NotBlank
    private String name;

    @Positive
    private int duration;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotNull
    private LocalDate releaseDate;
}
