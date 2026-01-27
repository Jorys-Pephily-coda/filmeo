package com.filmeo.filmeo.controller.admin;

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

import com.filmeo.filmeo.model.entity.Artist;
import com.filmeo.filmeo.model.service.ArtistService;




@Controller
@RequestMapping(path = "/admin/artists")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @GetMapping
    public String listArtists(Model templateModel) {
        List<Artist> artists = artistService.getAll();
        templateModel.addAttribute("artists", artists);
        templateModel.addAttribute("content", "admin/artist/list.html");
        return "base.html";
        
    }
    
    @GetMapping("/{id}")
    public String listArtistId(@PathVariable int id, Model templateModel) {
        Artist artist = artistService.getById(id);
        templateModel.addAttribute("artist", artist);
        templateModel.addAttribute("content", "admin/artist/artistFile");
        return "base.html";
    }

    @PostMapping("/add")
    public String addArtist(@ModelAttribute Artist artist, BindingResult br, Model model) {
        if(br.hasErrors()) {
            model.addAttribute("artist", artist);
            model.addAttribute("content", "admin/artist/form");
            return "base";
        }
        artistService.update(artist);
        return "redirect:/admin/artists";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("artist", new Artist());
        return "admin/artist/form";
    }

    @GetMapping("/{id}/delete")
    public String formDelete(@PathVariable Integer id) {
        Artist artist = artistService.getById(id);
        artistService.delete(artist);
        return "base";
    }

    @GetMapping("/{id}/modify")
    public String formModif(@PathVariable int id) {
        Artist artist = artistService.getById(id);
        artistService.update(artist);
        return "admin/artists";
    }

    @PostMapping("/{id}/modify")
    public String modifier(@ModelAttribute Artist artist, BindingResult br, Model model) {
        artistService.update(artist);
        return "admin/artists";
    }
}
