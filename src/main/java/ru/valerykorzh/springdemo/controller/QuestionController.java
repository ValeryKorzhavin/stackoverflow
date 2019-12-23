package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.service.AccountService;
import ru.valerykorzh.springdemo.service.QuestionService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final AccountService accountService;

    @GetMapping("/questions")
    public String findAll(Model model) {
        List<Question> questions = questionService.findAll();

        model.addAttribute("questions", questions);

        return "question/list";
    }

    @GetMapping("/questions/new")
    public String askQuestion(Model model) {

        model.addAttribute("question", new Question());

        return "question/new";
    }

    @PostMapping("/questions")
    public String saveQuestion(@Valid @ModelAttribute("question") Question question, Principal principal) {
        String userEmail = principal.getName();
        Account author = accountService
                .findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User with this email not found: " + userEmail));

        question.setAuthor(author);
        questionService.save(question);

        return "redirect:questions";
    }

    @GetMapping("/questions/{questionId}")
    public String findById(@PathVariable Long questionId) {
        Question question = questionService
                .findById(questionId)
                .orElseThrow(() -> new RuntimeException("questionId not exists"));

        return "question/view";
    }

}
