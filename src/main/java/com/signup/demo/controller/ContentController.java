package com.signup.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.signup.demo.model.MyAppUser;
import com.signup.demo.model.MyAppUserRepository;

@Controller
public class ContentController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup() {
        return "signup";
    }

    // Single mapping for /index that adds username to the model when logged in
    @GetMapping("/index")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            // principal.getName() should be the username stored by Spring Security
            MyAppUser user = myAppUserRepository.findByUsername(principal.getName())
                                .orElse(null);
            if (user != null) {
                model.addAttribute("username", user.getUsername());
                model.addAttribute("email", user.getEmail());
            }
        }
        return "index";
    }
}
