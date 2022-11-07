package ru.yandex.practicum.filmorate.storage;

import java.util.Map;

/**
 * @author Oleg Khilko
 */

public abstract class Storage<T> {

    public abstract T add(T t);

    public abstract T update(T t);

    public abstract Map<Long, T> getAll();

    public abstract T getById(Long id);

    public abstract void deleteAll();

    public abstract void deleteById(Long id);
}
