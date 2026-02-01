package com.filmeo.filmeo.controller.admin;

import com.filmeo.filmeo.model.entity.Artist;
import com.filmeo.filmeo.model.entity.Country;
import com.filmeo.filmeo.model.service.ArtistService;
import com.filmeo.filmeo.model.service.CountryService;
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
@RequestMapping(path = "/admin/artists")
public class AdminArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private CountryService countryService;

    @GetMapping
    public String listArtists(Model templateModel) {
        List<Artist> artists = artistService.getAll();
        templateModel.addAttribute("artists", artists);
        return "admin/artist/list";
    }

    @PostMapping("/add")
    public String addArtist(
        @ModelAttribute Artist artist,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            List<Country> countries = countryService.getAll();
            model.addAttribute("artist", artist);
            model.addAttribute("countries", countries);
            return "admin/artist/form";
        }
        artistService.update(artist);
        return "redirect:/admin/artists";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("artist", new Artist());
        List<Country> countries = countryService.getAll();
        model.addAttribute("countries", countries);
        return "admin/artist/form";
    }

    @GetMapping("/delete/{id}")
    public String formDelete(@PathVariable Integer id) {
        Artist artist = artistService.getById(id);
        artistService.delete(artist);
        return "redirect:/admin/artists";
    }

    @GetMapping("/edit/{id}")
    public String formModif(@PathVariable int id, Model model) {
        Artist artist = artistService.getById(id);
        List<Country> countries = countryService.getAll();
        model.addAttribute("artist", artist);
        model.addAttribute("countries", countries);
        return "admin/artist/form";
    }

    @PostMapping("/edit/{id}")
    public String modifier(
        @PathVariable int id,
        @ModelAttribute Artist artist,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            List<Country> countries = countryService.getAll();
            model.addAttribute("artist", artist);
            model.addAttribute("countries", countries);
            return "admin/artist/form";
        }
        artistService.update(artist);
        return "redirect:/admin/artists";
    }
}
