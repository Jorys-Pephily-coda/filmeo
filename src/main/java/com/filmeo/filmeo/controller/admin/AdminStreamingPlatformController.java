package com.filmeo.filmeo.controller.admin;

import com.filmeo.filmeo.model.entity.StreamingPlatform;
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

@Controller
@RequestMapping(path = "/admin/platforms")
public class AdminStreamingPlatformController {

    @Autowired
    private StreamingPlatformService platformService;

    @GetMapping
    public String listStreamingPlatforms(Model templateModel) {
        List<StreamingPlatform> platforms = platformService.getAll();
        templateModel.addAttribute("platforms", platforms);
        return "admin/platform/list.html";
    }

    @PostMapping("/add")
    public String addStreamingPlatform(
        @ModelAttribute StreamingPlatform platform,
        BindingResult br,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("platform", platform);
            model.addAttribute("content", "admin/platform/form");
            return "base";
        }
        platformService.update(platform);
        return "redirect:/admin/platform";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("platform", new StreamingPlatform());
        return "admin/platform/form";
    }

    @GetMapping("/delete/{id}/")
    public String formDelete(@PathVariable Integer id) {
        StreamingPlatform platform = platformService.getById(id);
        platformService.delete(platform);
        return "base";
    }

    @GetMapping("/edit/{id}/")
    public String formModif(@PathVariable int id) {
        StreamingPlatform platform = platformService.getById(id);
        platformService.update(platform);
        return "admin/platform";
    }

    @PostMapping("/edit/{id}/")
    public String modifier(
        @ModelAttribute StreamingPlatform platform,
        BindingResult br,
        Model model
    ) {
        platformService.update(platform);
        return "admin/platform";
    }
}
