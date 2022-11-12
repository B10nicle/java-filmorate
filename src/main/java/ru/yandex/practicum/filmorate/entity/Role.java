package ru.yandex.practicum.filmorate.entity;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Oleg Khilko
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "role")
@ToString(exclude = "users")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<User> users;

    public Role(String name) {
        this.name = name;
    }
}
