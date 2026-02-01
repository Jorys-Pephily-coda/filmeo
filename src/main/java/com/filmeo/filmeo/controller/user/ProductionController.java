package com.filmeo.filmeo.controller.user;

import com.filmeo.filmeo.model.entity.Production;
import com.filmeo.filmeo.model.entity.User;
import com.filmeo.filmeo.model.service.ProductionService;
import com.filmeo.filmeo.model.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/productions")
public class ProductionController {

    @Autowired
    private ProductionService productionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listProductions(Model templateModel) {
        List<Production> productions = productionService.getAll();
        templateModel.addAttribute("productions", productions);
        return "user/production/list.html";
    }

    @GetMapping("/{id}")
    public String listProductionId(@PathVariable int id, Model templateModel) {
        Production production = productionService.getById(id);
        templateModel.addAttribute("production", production);
        User user = getCurrentUser();
        templateModel.addAttribute("currentUser", user);
        boolean inWatchList = false;
        if (user != null && user.getWatchList() != null) {
            inWatchList = user.getWatchList().contains(production);
        }
        templateModel.addAttribute("inWatchList", inWatchList);
        // templateModel.addAttribute("content", "admin/production/productionFile");
        return "user/production/detail.html";
    }

    private User getCurrentUser() {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userService.getByUsername(username);
        }
        return null;
    }
}
