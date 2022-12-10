package ru.yandex.practicum.filmorate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Oleg Khilko
 */

@Data
@Builder
@AllArgsConstructor
public class User {

    private Integer id;

    @NonNull
    private String email;

    @NonNull
    private String login;

    @NonNull
    private String name;

    @NonNull
    private LocalDate birthday;

    private final Set<Integer> friends = new HashSet<>();
}
