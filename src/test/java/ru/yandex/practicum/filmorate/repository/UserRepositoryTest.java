package ru.yandex.practicum.filmorate.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.entity.Role;
import ru.yandex.practicum.filmorate.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleg Khilko
 */

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        var user = User.builder()
                .id(1L)
                .firstName("Inna")
                .lastName("Aberton")
                .email("khilko@gmail.com")
                .password("123")
                .role(new Role("USER"))
                .build();

        entityManager.persist(user);
    }

    @Test
    void search() {
        var user = userRepository.findById(1L).get();
        assertEquals(user.getFirstName(), "Inna");
    }
}