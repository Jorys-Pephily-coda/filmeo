package com.filmeo.filmeo.controller;

import com.filmeo.filmeo.model.entity.User;
import com.filmeo.filmeo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "")
public class BaseController {

    @Autowired
    private UserService userService;

    @GetMapping
    // @AuthenticationPrincipal can be null for unauthenticated users
    // This allows the "/" route to be accessible by everyone (permitAll in SecurityConfig)
    public String Home(
        @AuthenticationPrincipal UserDetails userDetails,
        Model model
    ) {
        // Only add roles if user is authenticated
        if (userDetails != null) {
            model.addAttribute("roles", userDetails.getAuthorities());
        }
        return "base.html";
    }

    @GetMapping("/user")
    public String getUser(
        @AuthenticationPrincipal UserDetails userDetails,
        Model model
    ) {
        User user = userService.getByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "user/user.html";
    }
}
