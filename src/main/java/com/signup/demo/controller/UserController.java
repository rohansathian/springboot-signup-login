package com.signup.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.signup.demo.model.MyAppUser;
import com.signup.demo.model.MyAppUserRepository;

@Controller
public class UserController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/update-user")
    public String updateUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            Principal principal) {

        // Get currently logged-in user
        MyAppUser user = myAppUserRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        // Update fields
        user.setUsername(username);
        user.setEmail(email);

        if (!password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        myAppUserRepository.save(user);

        return "redirect:/index";
    }
}
