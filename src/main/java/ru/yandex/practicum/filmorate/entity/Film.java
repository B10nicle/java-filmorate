package ru.yandex.practicum.filmorate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Oleg Khilko
 */

@Data
@Builder
@AllArgsConstructor
public class Film {

    private int id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private LocalDate releaseDate;

    private int duration;

    @NonNull
    private Mpa mpa;

    private final Set<Integer> likes = new HashSet<>();

    private final List<Genre> genres = new ArrayList<>();
}
