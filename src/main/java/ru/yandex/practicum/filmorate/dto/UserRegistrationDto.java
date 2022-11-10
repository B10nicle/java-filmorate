package ru.yandex.practicum.filmorate.dto;

import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author Oleg Khilko
 */

@Data
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
