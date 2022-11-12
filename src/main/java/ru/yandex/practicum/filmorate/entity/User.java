package ru.yandex.practicum.filmorate.entity;

import lombok.NoArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import javax.persistence.*;
import java.util.Set;

/**
 * @author Oleg Khilko
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Size(min = 8)
    @Column(name = "password")
    private String password;

    @Email
    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"))
    private Set<Film> likes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "my_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "his_id", referencedColumnName = "id"))
    private Set<User> friends;

    public User(String firstName,
                String lastName,
                String password,
                String email,
                Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
