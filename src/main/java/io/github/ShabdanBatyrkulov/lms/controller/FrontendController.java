package io.github.ShabdanBatyrkulov.lms.controller;

import io.github.ShabdanBatyrkulov.lms.dto.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FrontendController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("userRegistrationDto")) {
            model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        }
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}