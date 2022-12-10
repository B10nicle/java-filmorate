package ru.yandex.practicum.filmorate.controller;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleg Khilko
 */

@SpringBootTest
@AutoConfigureTestDatabase
class UserControllerTest {

    @Autowired
    private UserController controller;

    private User userSample;

    @BeforeEach
    public void initialize() {
        userSample = new User(
                1,
                "Anatoly16@gmail.com",
                "Anatoly16",
                "Anatoly",
                LocalDate.of(2010, 11, 15));
    }

    public User friend() {
        return new User(
                2,
                "Anatoly13@gmail.com",
                "Anatoly13",
                "Anatoly",
                LocalDate.of(2010, 12, 25));
    }

    @Test
    void emptyLoginExceptionTest() {
        userSample.setLogin("");
        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createUser(userSample)
        );
        assertEquals("Login is blank", exception.getMessage());
    }

    @Test
    void incorrectLoginExceptionTest() {
        userSample.setLogin("q w");
        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createUser(userSample)
        );
        assertEquals("Incorrect login", exception.getMessage());
    }

    @Test
    void nullExceptionTest() {
        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createUser(null)
        );
        assertEquals("User cannot be created", exception.getMessage());
    }

    @Test
    void emptyEmailExceptionTest() {
        userSample.setEmail("");
        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createUser(userSample)
        );
        assertEquals("Incorrect email", exception.getMessage());
    }

    @Test
    void incorrectDobExceptionTest() {
        userSample.setBirthday(LocalDate.of(2025, 6, 9));
        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createUser(userSample)
        );
        assertEquals("Incorrect DOB", exception.getMessage());
    }

    @Test
    void incorrectEmailExceptionTest() {
        userSample.setEmail("qwerty");
        var exception = assertThrows(
                ValidationException.class,
                () -> controller.createUser(userSample)
        );
        assertEquals("Incorrect email", exception.getMessage());
    }

    @Test
    void emptyNameExceptionTest() {
        userSample.setName("");
        var user = controller.createUser(userSample);

        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    void incorrectIdExceptionTest() {
        userSample.setId(-5);
        var exception = assertThrows(
                NotFoundException.class,
                () -> controller.updateUser(userSample)
        );
        assertEquals("User does not exist", exception.getMessage());
    }

    @Test
    void getUserByIdTest() {
        var user1 = controller.createUser(userSample);
        var user2 = controller.getUserById(user1.getId());

        assertNotNull(user2);
    }

    @Test
    void notFoundExceptionTest() {
        var exception = assertThrows(
                NotFoundException.class,
                () -> controller.getUserById(-5)
        );
        assertEquals("User does not exist", exception.getMessage());
    }

    @Test
    void userAndFriendTest() {
        var user1 = controller.createUser(userSample);
        var friend1 = controller.createUser(friend());
        controller.addFriend(user1.getId(), friend1.getId());
        var user2 = controller.getUserById(user1.getId());
        var friend2 = controller.getUserById(friend1.getId());

        assertFalse(friend2.getFriends().contains(user1.getId()));
        assertTrue(user2.getFriends().contains(friend1.getId()));
        assertEquals(friend2.getFriends().size(), 0);
    }

    @Test
    void userNotFoundExceptionTest() {
        var user = controller.createUser(userSample);
        var exception = assertThrows(
                NotFoundException.class,
                () -> controller.addFriend(user.getId(), -20)
        );
        assertEquals("User does not exist", exception.getMessage());
    }

    @Test
    void commonFriendReturnTest() {
        var user = controller.createUser(userSample);
        var friend = controller.createUser(friend());
        var common = controller.createUser(friend());

        controller.addFriend(user.getId(), common.getId());
        controller.addFriend(user.getId(), friend.getId());
        controller.addFriend(friend.getId(), common.getId());

        assertEquals(controller.getCommonFriends(user.getId(), common.getId()).size(), 0);
        assertEquals(controller.getCommonFriends(user.getId(), friend.getId()).size(), 1);
        assertFalse(controller.getCommonFriends(user.getId(), friend.getId()).isEmpty());
    }

    @Test
    void userNotFoundExceptionWhenRemoveTest() {
        var user = controller.createUser(userSample);
        var exception = assertThrows(
                NotFoundException.class,
                () -> controller.removeFriend(user.getId(), -20)
        );
        assertEquals("User does not exist", exception.getMessage());
    }

    @Test
    void removeFriendTest() {
        var user = controller.createUser(userSample);
        var friend = controller.createUser(friend());
        controller.addFriend(user.getId(), friend.getId());
        controller.removeFriend(user.getId(), friend.getId());

        assertFalse(user.getFriends().contains(friend.getId()));
        assertFalse(friend.getFriends().contains(user.getId()));
        assertEquals(friend.getFriends().size(), 0);
    }

    @Test
    void userFriendReturnTest() {
        var user = controller.createUser(userSample);
        var friend = controller.createUser(friend());
        controller.addFriend(user.getId(), friend.getId());
        var user2 = controller.getUserById(user.getId());

        assertTrue(user2.getFriends().contains(friend.getId()));
        assertEquals(user2.getFriends().size(), 1);
    }
}
