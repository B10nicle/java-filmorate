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
public class Film {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @Positive
    private int duration;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotNull
    private LocalDate releaseDate;

    public Film() {
    }
}
