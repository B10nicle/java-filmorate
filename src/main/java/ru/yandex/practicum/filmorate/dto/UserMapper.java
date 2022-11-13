package ru.yandex.practicum.filmorate.dto;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.yandex.practicum.filmorate.entity.Role;
import ru.yandex.practicum.filmorate.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Oleg Khilko
 */

@Component
public class UserMapper implements Mapper<User, UserDto> {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto mapToDto(User user) {
        var dto = new UserDto();
        dto.setPassword(passwordEncoder.encode(user.getPassword()));
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    @Override
    public User mapToEntity(UserDto userDto) {
        var user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(new Role("ROLE_USER"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        return user;
    }
}
