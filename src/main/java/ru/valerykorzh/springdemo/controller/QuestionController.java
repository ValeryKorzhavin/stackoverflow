package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.valerykorzh.springdemo.controller.exception.AccountNotFoundException;
import ru.valerykorzh.springdemo.controller.exception.QuestionNotFoundException;
import ru.valerykorzh.springdemo.domain.*;
import ru.valerykorzh.springdemo.dto.QuestionDto;
import ru.valerykorzh.springdemo.dto.mapper.QuestionMapper;
import ru.valerykorzh.springdemo.service.AccountService;
import ru.valerykorzh.springdemo.service.QuestionService;
import ru.valerykorzh.springdemo.service.TagService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import static ru.valerykorzh.springdemo.controller.ControllerConstants.QUESTIONS_PATH;

@Controller
@RequestMapping(QUESTIONS_PATH)
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final AccountService accountService;
    private final TagService tagService;
    private final QuestionMapper questionMapper;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
//                          @SortDefault(sort = {"id"}, direction = Sort.Direction.DESC) Sort sort) {


        Page<Question> questions = questionService.findAll(pageable);

//        Pageable pageable2 = PageRequest.of(pageable.getPageSize(), pageable.getPageNumber(), sort);
//        Page<Question> questions2 = questionService.findAll(pageable2);


        model.addAttribute("questions", questions);
        // add flag to answer that is true

        // Sorted by:
        // by votes - Most Votes (max rating)
        // by date desc - Newest
        // by activity - question that was last created, answered, or modified

        // Filters:
        // no answers
        // no accepted answer

        return "question/list";
    }

    @GetMapping("/new")
    public String askQuestion(Model model) {

        model.addAttribute("questionDto", new QuestionDto());

        return "question/new";
    }

    @PostMapping
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

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, Principal principal) {
        Question question = questionService
                .findById(id)
                .orElseThrow(() -> new RuntimeException("questionId not exists"));

        model.addAttribute("question", question);
        model.addAttribute("answer", new Answer());

        return "question/view";
    }

    @PatchMapping(value = "/{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map voteUp(@PathVariable Long id, Principal principal) {
        String userEmail = principal.getName();
        Account author = accountService.findByEmail(userEmail).orElseThrow(() -> new AccountNotFoundException(userEmail));
        Question question = questionService.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        question.removeNegativeVote(author);
        question.addPositiveVote(author);
        questionService.save(question);
        Integer rating = question.getRating();
        return Collections.singletonMap("rating", rating);
    }

    @PatchMapping(value = "/{id}/dislike", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map voteDown(@PathVariable Long id, Principal principal) {
        String userEmail = principal.getName();
        Account author = accountService.findByEmail(userEmail).orElseThrow(() -> new AccountNotFoundException(userEmail));
        Question question = questionService.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        question.removePositiveVote(author);
        question.addNegativeVote(author);
        questionService.save(question);
        Integer rating = question.getRating();
        return Collections.singletonMap("rating", rating);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteById(id);
        return "redirect:/questions";
    }

}
