package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.valerykorzh.springdemo.controller.exception.AccountNotFoundException;
import ru.valerykorzh.springdemo.controller.exception.AnswerNotFoundException;
import ru.valerykorzh.springdemo.controller.exception.QuestionNotFoundException;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Answer;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.service.AccountService;
import ru.valerykorzh.springdemo.service.AnswerService;
import ru.valerykorzh.springdemo.service.QuestionService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static ru.valerykorzh.springdemo.controller.ControllerConstants.ANSWERS_PATH;
import static ru.valerykorzh.springdemo.controller.ControllerConstants.QUESTIONS_PATH;

@Controller
@RequestMapping(ANSWERS_PATH)
@AllArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final AccountService accountService;
    @Qualifier("questionServiceImpl")
    private final QuestionService questionService;

    @GetMapping
    public String findAll(Model model) {
        List<Answer> answers = answerService.findAll();

        model.addAttribute("answers", answers);

        return "answer/list";
    }

    @PostMapping
    public String createAnswer(@Valid @ModelAttribute Answer answer,
                               Principal principal) {

        String userEmail = principal.getName();
        Account author = accountService.findByEmail(userEmail)
                .orElseThrow(() -> new AccountNotFoundException(userEmail));

        Long id = answer.getQuestion().getId();

        Question question = questionService.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        answer.setAuthor(author);
        answer.setQuestion(question);
        answerService.save(answer);

        return String.format("redirect:/questions/%d", id);
    }

    @PatchMapping(value = "/{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map voteUp(@PathVariable Long id, Principal principal) {
        String userEmail = principal.getName();
        Account author = accountService.findByEmail(userEmail).orElseThrow(() -> new AccountNotFoundException(userEmail));
        Answer answer = answerService.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        answer.removeNegativeVote(author);
        answer.addPositiveVote(author);
        answerService.save(answer);
        Integer rating = answer.getRating();
        return Collections.singletonMap("rating", rating);
    }

    @PatchMapping(value = "/{id}/dislike", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map voteDown(@PathVariable Long id, Principal principal) {
        String userEmail = principal.getName();
        Account author = accountService.findByEmail(userEmail).orElseThrow(() -> new AccountNotFoundException(userEmail));
        Answer answer = answerService.findById(id).orElseThrow(() -> new AnswerNotFoundException(id));
        answer.removePositiveVote(author);
        answer.addNegativeVote(author);
        answerService.save(answer);
        Integer rating = answer.getRating();
        return Collections.singletonMap("rating", rating);
    }

    @GetMapping("/edit/{id}")
    public String getEditAnswerForm(@PathVariable Long id, Model model) {
        Answer answer = answerService.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));

        model.addAttribute("answer", answer);

        return "answer/edit";
    }

    @PutMapping
    public String editAnswer(@Valid @ModelAttribute Answer answer) {
        answerService.save(answer);

        return String.format("redirect:%s/%d", QUESTIONS_PATH, answer.getQuestion().getId());
    }


}
