package ru.yandex.practicum.filmorate.repository.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.entity.Mpa;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.Optional;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Component
@RequiredArgsConstructor
public class MpaRepositoryImpl implements MpaRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getMpa() {
        return jdbcTemplate.query("SELECT * FROM mpa",
                (resultSet, rowNum) -> mapMpa(resultSet));
    }

    @Override
    public Optional<Mpa> getMpaById(int id) {
        var mpaRow = jdbcTemplate.queryForRowSet(
                "SELECT * FROM mpa WHERE id = ?", id);

        if (mpaRow.next()) {
            var mpa = new Mpa(
                    mpaRow.getInt("id"),
                    mpaRow.getString("name"));
            return Optional.of(mpa);
        } else {
            return Optional.empty();
        }
    }

    private Mpa mapMpa(ResultSet resultSet) throws SQLException {
        return new Mpa(resultSet.getInt("id"),
                resultSet.getString("name"));
    }
}
