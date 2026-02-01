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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductionService productionService;

    private User getCurrentUser() {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userService.getByUsername(username);
        }
        return null;
    }

    @PostMapping("/productions/{id}/watchlist")
    public String addToWatchList(@PathVariable int id) {
        User user = getCurrentUser();
        List<Production> watchList = user.getWatchList();
        Production production = productionService.getById(id);
        watchList.add(production);
        user.setWatchList(watchList);
        userService.update(user);
        return "redirect:/productions/" + id;
    }

    @GetMapping("/productions/{id}/watchlist/delete")
    public String DeleteFromWatchList(@PathVariable Integer id) {
        User user = getCurrentUser();
        List<Production> watchList = user.getWatchList();
        Production production = productionService.getById(id);
        watchList.remove(production);
        user.setWatchList(watchList);
        userService.update(user);
        return "redirect:/productions/" + id;
    }
}
