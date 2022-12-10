package ru.yandex.practicum.filmorate.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.yandex.practicum.filmorate.repository.mpa.MpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MpaRepositoryTest {
    private final MpaRepository repository;

    @Test
    void getMpaByIdTest() {
        assertThat(repository.getMpaById(5))
                .isPresent()
                .hasValueSatisfying(
                        mpa -> assertThat(mpa).hasFieldOrPropertyWithValue("name", "NC-17"));
    }

    @Test
    void getMpaTest() {
        var mpaList = repository.getMpa();

        assertEquals(mpaList.get(1).getName(), "PG");
        assertEquals(5, mpaList.size());
        assertFalse(mpaList.isEmpty());
    }
}
