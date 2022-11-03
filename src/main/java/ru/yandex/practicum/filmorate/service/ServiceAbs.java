package ru.yandex.practicum.filmorate.service;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public abstract class ServiceAbs<T> {

    public abstract T add(T t);

    public abstract T update(T t);

    public abstract List<T> getAll();

    public abstract T getById(int id);

    public abstract void deleteAll();

    public abstract void deleteById(int id);
}