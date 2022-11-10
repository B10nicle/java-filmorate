package ru.yandex.practicum.filmorate.entity;

import ru.yandex.practicum.filmorate.constraint.ReleaseDate;
import lombok.Data;

import javax.validation.constraints.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Oleg Khilko
 */

@Data
@Entity
public class Film {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Positive
    private int duration;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotNull
    @ReleaseDate
    private LocalDate releaseDate;

/*    @ManyToMany (mappedBy = "likedFilms")
    private Set<User> likes;

    @Override
    public int compareTo(Film o) {
        return o.getLikes().size() - this.getLikes().size();
    }*/
}
