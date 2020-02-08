package ru.valerykorzh.springdemo.controller;

import com.google.common.base.CaseFormat;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.valerykorzh.springdemo.controller.exception.AccountNotFoundException;
import ru.valerykorzh.springdemo.controller.exception.QuestionNotFoundException;
import ru.valerykorzh.springdemo.domain.*;
import ru.valerykorzh.springdemo.dto.QuestionDto;
import ru.valerykorzh.springdemo.dto.mapper.QuestionMapper;
import ru.valerykorzh.springdemo.service.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

import static ru.valerykorzh.springdemo.controller.ControllerConstants.QUESTIONS_PATH;

@Controller
@RequestMapping(QUESTIONS_PATH)
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final AccountService accountService;
    private final TagService tagService;
    private final QuestionMapper questionMapper;
    private final List<QuestionSortService> questionSortServices;

    @ModelAttribute("module")
    public String module() {
        return "questions";
    }

    @GetMapping
    public String findAll(Model model,
                          @RequestParam(value = "filters", required = false) String filters,
                          @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {

        if (!Objects.isNull(filters)) {
            List<String> filtersList = List.of(filters.split(","));
            System.out.println(filtersList);

//            filtersList.stream().map()
        }

        Optional<QuestionSortType> sortType = pageable
            .getSort()
            .get()
            .map(Sort.Order::getProperty)
            .map(type -> CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, type))
            .map(QuestionSortType::valueOf)
            .findFirst();

        sortType.flatMap(questionSortType -> questionSortServices
                .stream()
                .filter(service -> service.isSuitableFor(questionSortType))
                .findFirst()).ifPresent(service -> {
            model.addAttribute("questions", service.sort(pageable));
        });

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
