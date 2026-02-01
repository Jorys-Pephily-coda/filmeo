package com.filmeo.filmeo.controller.admin;

import com.filmeo.filmeo.model.entity.Genre;
import com.filmeo.filmeo.model.service.GenreService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin/genres")
public class AdminGenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping
    public String listGenres(Model templateModel) {
        List<Genre> genres = genreService.getAll();
        templateModel.addAttribute("genres", genres);
        return "admin/genre/list.html";
    }

    @GetMapping("/{id}")
    public String listGenreId(@PathVariable int id, Model templateModel) {
        Genre genre = genreService.getById(id);
        templateModel.addAttribute("genre", genre);
        templateModel.addAttribute("content", "admin/genre/genreFile");
        return "base.html";
    }

    @PostMapping("/add")
    public String addGenre(
        @ModelAttribute Genre genre,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("genre", genre);
            model.addAttribute("content", "admin/genre/form");
            return "base";
        }
        genreService.update(genre);
        return "redirect:/admin/genres";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("genre", new Genre());
        return "admin/genre/form";
    }

    @GetMapping("/delete/{id}/")
    public String formDelete(@PathVariable Integer id) {
        Genre genre = genreService.getById(id);
        genreService.delete(genre);
        return "base";
    }

    @GetMapping("/edit/{id}/")
    public String formModif(@PathVariable int id) {
        Genre genre = genreService.getById(id);
        genreService.update(genre);
        return "admin/genres";
    }

    @PostMapping("/edit/{id}/")
    public String modifier(
        @ModelAttribute Genre genre,
        BindingResult br,
        Model model
    ) {
        genreService.update(genre);
        return "admin/genres";
    }
}
