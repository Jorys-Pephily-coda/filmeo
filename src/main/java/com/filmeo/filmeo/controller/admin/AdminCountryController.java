package com.filmeo.filmeo.controller.admin;

import com.filmeo.filmeo.model.entity.Country;
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
@RequestMapping(path = "/admin/countries")
public class AdminCountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public String listCountrys(Model templateModel) {
        List<Country> countries = countryService.getAll();
        templateModel.addAttribute("countries", countries);
        return "admin/country/list.html";
    }

    @PostMapping("/add")
    public String addCountry(
        @ModelAttribute Country country,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("country", country);
            model.addAttribute("content", "admin/country/form");
            return "base";
        }
        countryService.update(country);
        return "redirect:/admin/countries";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("country", new Country());
        return "admin/country/form";
    }

    @GetMapping("/delete/{id}/")
    public String formDelete(@PathVariable Integer id) {
        Country country = countryService.getById(id);
        countryService.delete(country);
        return "base";
    }

    @GetMapping("/edit/{id}/")
    public String formModif(@PathVariable int id) {
        Country country = countryService.getById(id);
        countryService.update(country);
        return "admin/countries";
    }

    @PostMapping("/edit/{id}/")
    public String modifier(
        @ModelAttribute Country country,
        BindingResult br,
        Model model
    ) {
        countryService.update(country);
        return "admin/countries";
    }
}
