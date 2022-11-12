package ru.yandex.practicum.filmorate.entity;

import ru.yandex.practicum.filmorate.constraint.ReleaseDate;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Table(name = "film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Positive
    @Column(name = "duration")
    private int duration;

    @NotBlank
    @Size(max = 200)
    @Column(name = "description")
    private String description;

    @NotNull
    @ReleaseDate
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likes")
    private Set<User> likes;

/*    @Override
    public int compareTo(Film o) {
        return o.getLikes().size() - this.getLikes().size();
    }*/
}
