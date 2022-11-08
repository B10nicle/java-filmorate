package ru.yandex.practicum.filmorate.entity;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "user_film",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private Set<Film> likedFilms = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_film",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Film> likedUsers = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "from_user_id"),
            inverseJoinColumns = @JoinColumn(name = "to_user_id")
    )
    private Set<User> friends = new HashSet<>();
}
