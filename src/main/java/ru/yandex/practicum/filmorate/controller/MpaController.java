package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.entity.Mpa;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    private final MpaService service;

    @GetMapping("/{id}")
    public Mpa getMpa(@PathVariable int id) {
        return service.getMpa(id);
    }

    @GetMapping()
    public List<Mpa> getAll() {
        return service.getAllMpa();
    }
}
