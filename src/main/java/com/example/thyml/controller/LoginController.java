package com.example.thyml.controller;

import com.example.thyml.entity.Role;
import com.example.thyml.entity.User;
import com.example.thyml.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/auth/login-form")
    public String loginPage(String message, String error, String logout, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
//		List<Role> roles = new ArrayList<Role>(){{add(new Role("ROLE_ADMIN"));}};
        return "auth/login";
    }

    @PostMapping(value = {"/auth/login", "/auth/login/{message}"})
    public String loginsuccess(@PathVariable(value = "message", required = false) String message, Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String page = "";
        String roleAdmin = "ROLE_ADMIN";
        String roleUser = "ROLE_USER";
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(roleAdmin)) {
                page = "redirect:/user/list";
            } else if (authority.getAuthority().equals(roleUser)) {
                page = "redirect:/user/user-form";
            } else {
                page = "redirect:/logout";
            }
        }
        return page;
    }
}
