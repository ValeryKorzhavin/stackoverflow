package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.service.QuestionService;

import java.util.List;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final QuestionService questionService;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("module", "index from module");
    }

    @GetMapping
    public String index(Model model) {
        List<Question> questions = questionService.findAll();

        model.addAttribute("questions", questions);

        return "index";
    }

}
