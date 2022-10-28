package ru.yandex.practicum.filmorate.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Oleg Khilko
 */

@Data
public class Film {
    private LocalDate releaseDate;
    private String description;
    private int duration;
    private String name;
    private int id;
}
