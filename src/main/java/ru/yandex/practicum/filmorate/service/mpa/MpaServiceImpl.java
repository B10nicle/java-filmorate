package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.repository.mpa.MpaRepository;
import ru.yandex.practicum.filmorate.entity.Mpa;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Service
@AllArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final MpaRepository repository;

    @Override
    public Mpa getMpa(int id) {
        return repository.getMpaById(id).orElseThrow(() -> {
            throw new NotFoundException("MPA with ID#" + id + " does not exist.");
        });
    }

    @Override
    public List<Mpa> getAllMpa() {
        return repository.getMpa();
    }
}
