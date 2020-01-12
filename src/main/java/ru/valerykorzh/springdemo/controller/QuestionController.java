package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.valerykorzh.springdemo.domain.*;
import ru.valerykorzh.springdemo.dto.QuestionDto;
import ru.valerykorzh.springdemo.dto.mapper.QuestionMapper;
import ru.valerykorzh.springdemo.service.AccountService;
import ru.valerykorzh.springdemo.service.QuestionService;
import ru.valerykorzh.springdemo.service.TagService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final AccountService accountService;
    private final TagService tagService;
    private final QuestionMapper questionMapper;

    @GetMapping("/questions")
    public String findAll(Model model) {
        List<Question> questions = questionService.findAll();

        model.addAttribute("questions", questions);

        return "question/list";
    }

    @GetMapping("/questions/new")
    public String askQuestion(Model model) {

        model.addAttribute("questionDto", new QuestionDto());

        return "question/new";
    }

    @PostMapping("/questions")
    public String saveQuestion(@Valid @ModelAttribute QuestionDto questionDto, Principal principal) {
        String userEmail = principal.getName();
        Account author = accountService
                .findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User with this email not found: " + userEmail));

        Question question = questionMapper.toQuestion(questionDto, tagService);

        question.setAuthor(author);
        questionService.save(question);

        return "redirect:questions";
    }

    @GetMapping("/questions/{id}")
    public String findById(@PathVariable Long id, Model model, Principal principal) {
        Question question = questionService
                .findById(id)
                .orElseThrow(() -> new RuntimeException("questionId not exists"));



        model.addAttribute("question", question);
        model.addAttribute("answer", new Answer());

//        System.out.println(answer.getQuestion().getTitle());
//        System.out.println(answer.getAuthor().getEmail());

        return "question/view";
    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteById(id);
        return "redirect:/questions";
    }

}
