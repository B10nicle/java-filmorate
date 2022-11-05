package ru.yandex.practicum.filmorate.controller;

import org.springframework.data.repository.query.Param;
import ru.yandex.practicum.filmorate.service.Services;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Controller
@RequestMapping("/films")
@Tag(name = "film-controller", description = "Film Service API")
public class FilmController {
    private final Services<Film> service;

    public FilmController(Services<Film> service) {
        this.service = service;
    }

    @GetMapping()
    @ApiOperation("Show all films")
    public String getFilms(Model model, @Param("keyword") String keyword) {
        var films = service.search(keyword);

        if (films.isEmpty())
            return "search-error";

        model.addAttribute("films", films);
        model.addAttribute("keyword", keyword);
        log.debug("List of all films: " + films);

        return "films";
    }

    @GetMapping("/add-film")
    @ApiOperation("Show form to add a new film")
    public String showAddForm(Model model) {
        var film = new Film();
        log.debug("Request to show add form for film: {}", film);
        model.addAttribute("film", film);
        return "add-film";
    }

    @PostMapping("/add")
    @ApiOperation("Add film")
    public String addFilm(@Valid @ModelAttribute("film") Film film) {
        log.debug("Request to create film: {}", film);
        service.add(film);
        return "redirect:/films";
    }

    @GetMapping("/edit/{id}")
    @ApiOperation("Edit film by ID")
    public ModelAndView showEditFilmPage(@PathVariable("id") Long id) {
        var editView = new ModelAndView("edit-film");
        var film = service.get(id);
        editView.addObject("film", film);
        return editView;
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("Delete film by ID")
    public String deleteFilm(@PathVariable("id") Long id) {
        log.debug("Request to delete film: ID {}", id);
        service.delete(id);
        return "redirect:/films";
    }
}
