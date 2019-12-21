package ru.valerykorzh.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {

    @GetMapping("/questions")
    public String findAll() {

        return "account/list";
    }

}
