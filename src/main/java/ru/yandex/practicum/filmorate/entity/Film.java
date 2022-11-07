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
public class Film implements Comparable<Film> {
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

    private Set<Long> likes = new HashSet<>();

    @Override
    public int compareTo(Film o) {
        return o.getLikes().size() - this.getLikes().size();
    }
}
