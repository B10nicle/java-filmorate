package ru.yandex.practicum.filmorate.service;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public abstract class ServiceAbs<T> {

    public abstract T add(T t);

    public abstract T update(T t);

    public abstract T getById(Long id);

    public abstract List<T> getAll();

    public abstract void deleteAll();

    public abstract void delete(Long id);
}
