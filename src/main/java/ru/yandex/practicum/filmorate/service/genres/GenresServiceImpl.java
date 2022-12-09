package ru.yandex.practicum.filmorate.service.genres;

import ru.yandex.practicum.filmorate.repository.genres.GenresRepository;
import ru.yandex.practicum.filmorate.exception.DoesntFindException;
import ru.yandex.practicum.filmorate.entity.Genre;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Service
@AllArgsConstructor
public class GenresServiceImpl implements GenresService {
    private final GenresRepository repository;

    @Override
    public Genre getGenre(int id) {
        return repository.getGenreById(id).orElseThrow(() -> {
            throw new DoesntFindException("Genre with ID#" + id + " does not exist.");
        });
    }

    @Override
    public List<Genre> getAllGenres() {
        return repository.getGenres();
    }
}
