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
import java.util.function.Consumer;

import static ru.valerykorzh.springdemo.controller.ControllerConstants.ANSWERS_PATH;

@Controller
@RequestMapping(ANSWERS_PATH)
@AllArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final AccountService accountService;
    private final QuestionService questionService;

    @GetMapping
    public String findAll(Model model) {
        List<Answer> answers = answerService.findAll();

        model.addAttribute("answers", answers);

        return "answer/list";
    }

    @PostMapping
    public String createAnswer(@Valid @ModelAttribute Answer answer,
                               @RequestParam("questionId") Long id,
                               Principal principal) {
        String userEmail = principal.getName();
        Account author = accountService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("This user email not found: " + userEmail));
        Question question = questionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Question id not found: " + id));


        if (answer.getId() != null) {
            Answer editedAnswer = answerService.findById(answer.getId())
                    .orElseThrow(() -> new RuntimeException("Error occurred: answer not found"));
            editedAnswer.setAuthor(author);
            editedAnswer.setContent(answer.getContent());
            answerService.save(editedAnswer);
        } else {
            answer.setAuthor(author);
            answer.setQuestion(question);
            answerService.save(answer);
        }


        return String.format("redirect:/questions/%d", id);
    }

    @GetMapping("/edit/{id}")
    public String getEditAnswerForm(@PathVariable Long id, Model model) {
//        Runnable error = () -> model.addAttribute("error", String.format("Answer with id=%d not found", id));
//        Consumer<Answer> fillModel = answer -> model.addAttribute("answer", answer);
//        answerService.findById(id).ifPresentOrElse(fillModel, error);
        Answer answer = answerService.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Answer with id=%d not found", id)));
        Question question = answer.getQuestion();
        model.addAttribute("question", question);
        model.addAttribute("answer", answer);

        return "question/view";
    }


}
