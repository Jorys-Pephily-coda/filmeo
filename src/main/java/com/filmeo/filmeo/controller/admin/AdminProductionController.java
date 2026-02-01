package com.filmeo.filmeo.controller.admin;

import com.filmeo.filmeo.model.entity.Artist;
import com.filmeo.filmeo.model.entity.Availability;
import com.filmeo.filmeo.model.entity.Genre;
import com.filmeo.filmeo.model.entity.Production;
import com.filmeo.filmeo.model.entity.Series;
import com.filmeo.filmeo.model.service.ArtistService;
import com.filmeo.filmeo.model.service.AvailabilityService;
import com.filmeo.filmeo.model.service.CountryService;
import com.filmeo.filmeo.model.service.GenreService;
import com.filmeo.filmeo.model.service.ProductionService;
import com.filmeo.filmeo.model.service.StreamingPlatformService;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/admin/productions")
public class AdminProductionController {

    @Autowired
    private ProductionService productionService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private StreamingPlatformService streamingPlatformService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private CountryService countryService;

    @GetMapping
    public String listProductions(Model templateModel) {
        List<Production> productions = productionService.getAll();
        templateModel.addAttribute("productions", productions);
        return "admin/production/list.html";
    }

    @GetMapping("/{id}")
    public String listProductionId(@PathVariable int id, Model templateModel) {
        Production production = productionService.getById(id);
        templateModel.addAttribute("production", production);
        templateModel.addAttribute(
            "content",
            "admin/production/productionFile"
        );
        return "base.html";
    }

    @GetMapping("/add")
    public String form(Model model) {
        Production production = new Production();
        production.setSeriesDetails(new Series()); // ← IMPORTANT : initialise seriesDetails
        
        model.addAttribute("production", production);
        model.addAttribute("artists", artistService.getAll());
        model.addAttribute("countries", countryService.getAll());
        
        return "admin/production/form";
    }

    @PostMapping("/add")
    public String addProduction(
        @ModelAttribute Production production,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("production", production);
            model.addAttribute("artists", artistService.getAll());
            model.addAttribute("countries", countryService.getAll());
            return "admin/production/form";
        }
        
        // Si c'est un film, on supprime seriesDetails
        if ("movie".equals(production.getType())) {
            production.setSeriesDetails(null);
        } else if (production.getSeriesDetails() != null) {
            // Si c'est une série, on lie production à seriesDetails
            production.getSeriesDetails().setProduction(production);
        }
        
        productionService.update(production);
        return "redirect:/admin/productions";
    }

    @GetMapping("/edit/{id}")
    public String formEdit(@PathVariable Integer id, Model model) {
        Production production = productionService.getById(id);
        
        // Si c'est une série mais seriesDetails est null, on l'initialise
        if ("series".equals(production.getType()) && production.getSeriesDetails() == null) {
            production.setSeriesDetails(new Series());
        }
        
        model.addAttribute("production", production);
        model.addAttribute("artists", artistService.getAll());
        model.addAttribute("countries", countryService.getAll());
        
        return "admin/production/form";
    }

    @GetMapping("/delete/{id}/")
    public String formDelete(@PathVariable Integer id) {
        Production production = productionService.getById(id);
        productionService.delete(production);
        return "base";
    }

    @PostMapping("/edit/{id}/")
    public String modifier(
        @ModelAttribute Production production,
        BindingResult br,
        Model model
    ) {
        productionService.update(production);

        return "admin/productions";
    }

    @GetMapping("/{id}/genre")
    public String addProductionGenre(@PathVariable int id, Model model) {
        Production production = productionService.getById(id);
        List<Genre> genres = genreService.getAll();
        model.addAttribute("production", production);
        model.addAttribute("genres", genres);
        return "admin/production/genreform";
    }

    @PostMapping("/{id}/genre")
    public String submitProductionGenre(
        @PathVariable int id,
        @RequestParam("genreId") int genreId
    ) {
        Production production = productionService.getById(id);
        Genre genre = genreService.getById(genreId);

        production.getGenres().add(genre);
        productionService.update(production);

        return "redirect:/productions/" + id;
    }


    @GetMapping("/{id}/genre/delete/{genreId}")
    public String deleteProductionGenre(
        @PathVariable int id,
        @PathVariable int genreId
    ) {
        Production production = productionService.getById(id);
        Genre genre = genreService.getById(genreId);
        List<Genre> genres = production.getGenres();
        genres.remove(genre);
        production.setGenres(genres);
        productionService.update(production);
        return "redirect:/productions/" + id;
    }

    /* Availability */
    @GetMapping("/{id}/availability")
    public String addProductionAvailability(@PathVariable int id, Model model) {
        Production production = productionService.getById(id);
        Availability availability = new Availability();
        model.addAttribute("production", production);
        model.addAttribute("availability", availability);
        model.addAttribute("platforms", streamingPlatformService.getAll());
        return "admin/production/availabilityform";
    }

    @PostMapping("/{id}/availability")
    public String submitProductionAvailability(
        @PathVariable int id,
        @ModelAttribute Availability availability,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            Production production = productionService.getById(id);
            model.addAttribute("production", production);
            model.addAttribute("availability", availability);
            model.addAttribute("platforms", streamingPlatformService.getAll());
            return "admin/production/availabilityform";
        }

        Production production = productionService.getById(id);
        availability.setProduction(production);

        availabilityService.update(availability);

        return "redirect:/productions/" + id;
    }


    @GetMapping("/{id}/availability/delete/{availabilityId}")
    public String deleteProductionAvailability(
        @PathVariable int id,
        @PathVariable int availabilityId
    ) {
        Production production = productionService.getById(id);
        Availability availability = availabilityService.getById(availabilityId);
        List<Availability> availabilities = production.getAvailabilities();
        availabilities.remove(availability);
        production.setAvailabilities(availabilities);
        productionService.update(production);
        return "redirect:/productions/" + id;
    }

    /* actor */
    @GetMapping("/{id}/actor")
    public String addProductionActor(@PathVariable int id, Model model) {
        Production production = productionService.getById(id);
        List<Artist> actors = artistService.getAll();
        model.addAttribute("production", production);
        model.addAttribute("actors", actors);
        return "admin/production/actorform";
    }

    @PostMapping("/{id}/actor")
    public String submitProductionActor(
        @PathVariable int id,
        @ModelAttribute Artist actor,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            Production production = productionService.getById(id);
            List<Artist> actors = artistService.getAll();
            model.addAttribute("production", production);
            model.addAttribute("actors", actors);
            model.addAttribute("platforms", streamingPlatformService.getAll());
            return "admin/production/actorform";
        }

        Production production = productionService.getById(id);
        production.getArtists().add(actor);
        productionService.update(production);

        return "redirect:/productions/" + id;
    }


    @GetMapping("/{id}/actor/delete/{actorId}")
    public String deleteProductionActor(
        @PathVariable int id,
        @PathVariable int actorId
    ) {
        Production production = productionService.getById(id);
        Artist actor = artistService.getById(actorId);
        List<Artist> artists = production.getArtists();
        artists.remove(actor);
        production.setArtists(artists);
        productionService.update(production);
        return "redirect:/productions/" + id;
    }
}
