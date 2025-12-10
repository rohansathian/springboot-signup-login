package com.signup.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ContentController {

    // @Autowired
    // private MyAppUserRepository myAppUserRepository;

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
    public String home() {
        return "index";
    }

    @GetMapping("error/404")
    public String error404() {
        return "error/404";
    }
    
    
}
