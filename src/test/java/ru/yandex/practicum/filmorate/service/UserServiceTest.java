package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.DisplayName;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.yandex.practicum.filmorate.entity.Role;
import ru.yandex.practicum.filmorate.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleg Khilko
 */

@SpringBootTest
class UserServiceTest {

    private final Long POSITIVE_CASE_ID = 1L;
    private final Long NEGATIVE_CASE_ID = 999L;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        var user = User.builder()
                .id(1L)
                .firstName("Oleg")
                .lastName("Khilko")
                .email("khilkoleg@gmail.com")
                .role(new Role("USER"))
                .build();

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    }

    @Test
    void save() {
    }

    @Test
    void add() {
    }

    @Test
    void getById_POSITIVE_CASE() {
        var found = userService.getById(POSITIVE_CASE_ID);
        assertEquals(POSITIVE_CASE_ID, found.getId());
    }

    @Test
    void getById_NEGATIVE_CASE() {
        assertThrows(DoesntExistException.class, () ->  userService.getById(NEGATIVE_CASE_ID));
    }

    @Test
    void search() {
    }

    @Test
    void delete() {
    }

    @Test
    void loadUserByUsername() {
    }
}
