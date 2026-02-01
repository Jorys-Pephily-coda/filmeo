package com.filmeo.filmeo.controller.admin;

import com.filmeo.filmeo.model.entity.Availability;
import com.filmeo.filmeo.model.service.AvailabilityService;
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
@RequestMapping(path = "/admin/productions/{productionId}/availability")
public class AdminAvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/add")
    public String addAvailability(
        @ModelAttribute Availability availability,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("availability", availability);
            model.addAttribute("content", "admin/availability/form");
            return "base";
        }
        availabilityService.update(availability);
        return "redirect:/admin/availabilities";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("availability", new Availability());
        return "admin/availability/form";
    }

    @GetMapping("/delete/{id}/")
    public String formDelete(@PathVariable Integer id) {
        Availability availability = availabilityService.getById(id);
        availabilityService.delete(availability);
        return "base";
    }

    @GetMapping("/edit/{id}/")
    public String formModif(@PathVariable int id) {
        Availability availability = availabilityService.getById(id);
        availabilityService.update(availability);
        return "admin/availabilities";
    }

    @PostMapping("/edit/{id}/")
    public String modifier(
        @ModelAttribute Availability availability,
        BindingResult br,
        Model model
    ) {
        availabilityService.update(availability);
        return "admin/availabilities";
    }
}
