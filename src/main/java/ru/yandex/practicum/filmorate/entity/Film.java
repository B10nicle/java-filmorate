package ru.yandex.practicum.filmorate.entity;

import ru.yandex.practicum.filmorate.constraint.ReleaseDate;
import org.hibernate.Hibernate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Film film = (Film) o;
        return id != null && Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
