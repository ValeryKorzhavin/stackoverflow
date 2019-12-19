package ru.valerykorzh.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("module", "index from module");
    }

    @GetMapping
    public String index() {
        return "index";
    }

}
