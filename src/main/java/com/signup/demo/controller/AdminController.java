package com.signup.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.signup.demo.model.MyAppUser;
import com.signup.demo.model.MyAppUserRepository;
import com.signup.demo.security.ActiveUserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MyAppUserRepository userRepository;
    private final ActiveUserService activeUserService;

    public AdminController(MyAppUserRepository userRepository, ActiveUserService activeUserService){
        this.userRepository = userRepository;
        this.activeUserService = activeUserService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        // simple admin check - replace with proper role checks later
        if (!isAdmin(principal)) {
            return "redirect:/login?accessDenied";
        }

        List<MyAppUser> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("activeCount", activeUserService.getActiveCount());
        model.addAttribute("activeUsers", activeUserService.getActiveUsers());
        return "admin_dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model, Principal principal){
        if (!isAdmin(principal)) {
            return "redirect:/login?accessDenied";
        }
        var user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "admin_edit_user";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute MyAppUser user, Principal principal){
        if (!isAdmin(principal)) {
            return "redirect:/login?accessDenied";
        }
        // If password is empty, keep existing password
        var existing = userRepository.findById(user.getId()).orElseThrow();
        if (user.getPassword() == null || user.getPassword().isBlank()){
            user.setPassword(existing.getPassword());
        } else {
            // encode - autowire encoder or call repository save after encoding. Example below assumes plain text
            // TODO: inject PasswordEncoder and encode
        }
        userRepository.save(user);
        return "redirect:/admin/dashboard";
    }

    private boolean isAdmin(Principal principal){
        // quick check: treat user with username 'devtest' as admin for now
        if (principal == null) return false;
        return "Fawa".equals(principal.getName());
    }
}
