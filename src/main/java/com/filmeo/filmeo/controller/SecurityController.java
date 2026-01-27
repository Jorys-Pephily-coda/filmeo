package com.filmeo.filmeo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.filmeo.filmeo.model.entity.User;
import com.filmeo.filmeo.model.service.UserService;




@Controller
public class SecurityController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "security/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "security/register";
    }

    @PostMapping("/do-register")
    public String registerUser(@ModelAttribute("user") User userToRegister) {
        userService.register(userToRegister);
        return "redirect:/connexion?success";
    }    
    
}

