package ru.yandex.practicum.filmorate.dto;

/**
 * @author Oleg Khilko
 */

public interface Mapper<U, D> {

    D mapToDto(U u);

    U mapToEntity(D d);
}
