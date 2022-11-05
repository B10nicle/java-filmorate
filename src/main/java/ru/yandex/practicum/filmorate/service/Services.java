package ru.yandex.practicum.filmorate.service;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public abstract class Services<T> {

    public abstract T add(T t);

    public abstract T get(Long id);

    public abstract void delete(Long id);

    public abstract List<T> search(String keyword);

}
