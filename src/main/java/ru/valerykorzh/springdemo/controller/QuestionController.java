package ru.valerykorzh.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.valerykorzh.springdemo.domain.Question;

@Controller
public class QuestionController {

    @GetMapping("/questions")
    public String findAll() {

        return "question/list";
    }

    @GetMapping("/questions/new")
    public String askQuestion(Model model) {

        model.addAttribute("question", new Question());

        return "question/new";
    }

}
