package com.budgettrip.controller;

import com.budgettrip.dto.UserRegistrationDto;
import com.budgettrip.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 1. Show the Registration Form (GET request)
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserRegistrationDto user = new UserRegistrationDto();
        model.addAttribute("user", user);
        return "register"; // This looks for register.html
    }

    // 2. Process the Registration Form (POST request)
    @PostMapping("/register")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserRegistrationDto userDto,
                                      BindingResult result,
                                      Model model) {
        // If there are validation errors (like empty email), reload the form
        if (result.hasErrors()) {
            return "register";
        }

        // Try to save the user
        try {
            userService.saveUser(userDto);
        } catch (Exception e) {
            // If email already exists or other error, show error on form
            result.rejectValue("email", "error.user", "An account already exists for this email.");
            return "register";
        }

        // If successful, redirect to login page with a success message
        return "redirect:/login?success";
    }

    // 3. Show Login Page
    @GetMapping("/login")
    public String login() {
        return "login"; // This looks for login.html
    }
}

