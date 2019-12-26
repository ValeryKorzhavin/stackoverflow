package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Answer;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.service.AccountService;
import ru.valerykorzh.springdemo.service.AnswerService;
import ru.valerykorzh.springdemo.service.QuestionService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final AccountService accountService;
    private final QuestionService questionService;

    @GetMapping("/answers")
    public String findAll(Model model) {
        List<Answer> answers = answerService.findAll();

        model.addAttribute("answers", answers);

        return "answer/list";
    }

    @PostMapping("/answers")
    public String createAnswer(@Valid @ModelAttribute Answer answer,
                               @RequestParam Long questionId,
                               Principal principal) {
        String userEmail = principal.getName();
        Account author = accountService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("This user email not found: " + userEmail));


        Question question = questionService.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question id not found: " + questionId));
        answer.setAuthor(author);
        answer.setQuestion(question);
        answerService.save(answer);

        return String.format("redirect:/questions/%d", questionId);
    }


}
