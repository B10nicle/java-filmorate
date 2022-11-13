package ru.yandex.practicum.filmorate.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Size(min = 8, message = "The password should be more than 8 letters")
    private String password;

    @Email (message = "Please insert valid email")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(firstName, userDto.firstName)
                && Objects.equals(lastName, userDto.lastName)
                && Objects.equals(password, userDto.password)
                && Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, password, email);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
