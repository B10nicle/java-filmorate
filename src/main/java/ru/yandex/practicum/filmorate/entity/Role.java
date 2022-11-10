package ru.yandex.practicum.filmorate.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Oleg Khilko
 */

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

}
