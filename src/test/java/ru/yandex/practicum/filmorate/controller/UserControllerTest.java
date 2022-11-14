package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.entity.Role;
import ru.yandex.practicum.filmorate.entity.User;
import ru.yandex.practicum.filmorate.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Oleg Khilko
 */

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("Inna")
                .lastName("Aberton")
                .email("khilko@gmail.com")
                .password("123")
                .role(new Role("USER"))
                .build();
    }

    @Test
    void getUsers() {
    }

    @Test
    void getUserById() throws Exception {
        Mockito.when(userService.getById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/add/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andExpect(jsonPath("$.firstName")
                        .value(user.getFirstName()));
    }

    @Test
    void showAddForm() {
    }

    @Test
    void addUser() throws Exception {
        var inputUser = User.builder()
                .firstName("Inna")
                .lastName("Aberton")
                .email("khilko@gmail.com")
                .password("123")
                .role(new Role("USER"))
                .build();

        Mockito.when(userService.add(inputUser)).thenReturn(user);

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"firstName\": \"dolore\",\n" +
                                "  \"lastName\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"password\": \"123\",\n" +
                                "  \"role\": \"USER\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void showEditForm() {
    }

    @Test
    void editUser() {
    }

    @Test
    void deleteUser() {
    }
}