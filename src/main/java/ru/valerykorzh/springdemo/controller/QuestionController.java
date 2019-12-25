package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
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
        // make list of dto's
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

    @GetMapping("/questions/{questionId}")
    public String findById(@PathVariable Long questionId) {
        Question question = questionService
                .findById(questionId)
                .orElseThrow(() -> new RuntimeException("questionId not exists"));

        return "question/view";
    }

    @DeleteMapping("/questions/{id}")
    public String deleteById(@PathVariable Long id) {
        questionService.deleteById(id);
        return "redirect:questions";
    }


//    @GetMapping("/questions/delete")
//    public String deleteById(@RequestParam("id") Long questionId) {
//        questionService.deleteById(questionId);
//        return "redirect:/questions";
//        // via link
//    }

}
