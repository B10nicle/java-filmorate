package ru.yandex.practicum.filmorate.service;

/**
 * @author Oleg Khilko
 */

public abstract class Services<T> {

    public abstract T add(T t);

    public abstract T update(T t);

    public abstract Iterable<T> getAll();

    public abstract T getById(Long id);

    public abstract void deleteAll();

    public abstract void deleteById(Long id);

}