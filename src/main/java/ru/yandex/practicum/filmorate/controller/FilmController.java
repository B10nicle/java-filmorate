package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.entity.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import ru.yandex.practicum.filmorate.entity.Film;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/films")
@Tag(name = "film-controller", description = "Film Service API")
public class FilmController {
    private final FilmService filmService;
    private final UserService userService;

    @GetMapping()
    @ApiOperation("Show all films")
    public String getFilms(Model model,
                           @Param("keyword") String keyword) {
        var films = filmService.search(keyword);

        if (films.isEmpty())
            return "search-error";

        model.addAttribute("keyword", keyword);
        model.addAttribute("films", films);
        log.debug("List of all films: " + films);
        return "films";
    }

    @GetMapping("/{id}")
    @ApiOperation("Get film by ID")
    public String getFilmById(Model model,
                              @PathVariable("id") Long id) {
        var film = filmService.getById(id);
        log.debug("Request to get film by ID {}", id);
        model.addAttribute("film", film);
        return "get-film-by-id";
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
        filmService.add(film);
        return "redirect:/films";
    }

    @GetMapping("/{id}/edit")
    @ApiOperation("Show form to edit a film")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        log.debug("Request to edit film with ID: {}", id);
        var editView = new ModelAndView("edit-film");
        var film = filmService.getById(id);
        editView.addObject("film", film);
        return editView;
    }

    @PostMapping("/{id}/edit")
    @ApiOperation("Edit film")
    public String editFilm(@Valid @ModelAttribute("film") Film film) {
        log.debug("Request to edit film: {}", film);
        filmService.add(film);
        return "redirect:/films/{id}";
    }

    @GetMapping("/{id}/delete")
    @ApiOperation("Delete film")
    public String deleteFilm(@PathVariable("id") Long id) {
        log.debug("Request to delete film: ID {}", id);
        filmService.delete(id);
        return "redirect:/films";
    }
    
    @GetMapping("/popular")
    @ApiOperation("Get the most popular films")
    public String getPopularFilms(Model model,
                                  @RequestParam(defaultValue = "10") int count) {
        log.debug("Request to show {} most popular films", count);
        var films = filmService.getPopularFilms(count);
        model.addAttribute("films", films);
        return "popular";
    }

    @PostMapping("/{id}/like/{userId}/add")
    @ApiOperation("Add like")
    public String addLike(@PathVariable Long userId,
                          @PathVariable Long id,
                          Model model) {
        log.debug("User with ID: {} added like to the film with ID: {}", userId, id);
        var likes = filmService.addLike(userId, id);
        model.addAttribute("likes", likes);
        return "redirect:/users/{id}";
    }

    @GetMapping("/{id}/like/{userId}/delete")
    @ApiOperation("Delete like")
    public String deleteLike(@PathVariable Long userId,
                             @PathVariable Long id,
                             Model model) {
        log.debug("User with ID: {} deleted like from the film with ID: {}", userId, id);
        var likes = filmService.deleteLike(userId, id);
        model.addAttribute("likes", likes);
        return "redirect:/users/{id}";
    }
}
