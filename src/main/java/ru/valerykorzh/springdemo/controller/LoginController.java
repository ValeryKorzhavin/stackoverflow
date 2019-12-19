package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final MessageSource messageSource;

    @GetMapping
    public String loginPage() {
        return "login";
    }

    @ModelAttribute
    public void showError(Model model, String error, Locale locale) {
        if (error != null) {
            String errorMessage = messageSource.getMessage("error.login", null, locale);
            model.addAttribute("error", errorMessage);
        }
    }

}
