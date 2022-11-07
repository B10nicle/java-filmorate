package ru.yandex.practicum.filmorate.entity;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.Comparator;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Oleg Khilko
 */

@Data
public class Film implements Comparator<Film> {
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
    public int compare(Film o1, Film o2) {
        return o1.getLikes().size() - o2.getLikes().size();
    }

    @Override
    public Comparator<Film> reversed() {
        return Comparator.super.reversed();
    }
}
