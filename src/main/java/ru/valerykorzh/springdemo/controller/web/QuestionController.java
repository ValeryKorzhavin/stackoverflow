package ru.valerykorzh.springdemo.controller.web;

import com.google.common.base.CaseFormat;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.Formatter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.valerykorzh.springdemo.controller.exception.AccountNotFoundException;
import ru.valerykorzh.springdemo.controller.exception.QuestionNotFoundException;
import ru.valerykorzh.springdemo.controller.exception.TagNotFoundException;
import ru.valerykorzh.springdemo.domain.*;
import ru.valerykorzh.springdemo.service.*;
import ru.valerykorzh.springdemo.service.impl.question.QuestionSortType;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static ru.valerykorzh.springdemo.controller.ControllerConstants.QUESTIONS_PATH;

@Controller
@RequestMapping(QUESTIONS_PATH)
@AllArgsConstructor
public class QuestionController {

    private static final String TEMPLATE_DIR = "question";
    private static final String NEW_TEMPLATE = TEMPLATE_DIR + "/new";
    private static final String LIST_TEMPLATE = TEMPLATE_DIR + "/list";
    private static final String EDIT_TEMPLATE = TEMPLATE_DIR + "/edit";
    private static final String VIEW_TEMPLATE = TEMPLATE_DIR + "/view";

    private final QuestionService questionService;
    private final AccountService accountService;
    private final TagService tagService;
    private final List<QuestionSortService> questionSortServices;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new Formatter<Set<Tag>>() {
            @Override
            public Set<Tag> parse(String s, Locale locale) throws ParseException {
                Set<Tag> tagSet = new HashSet<>();
                Arrays.stream(s.split("\\s+"))
                        .forEach(tag -> tagService.findByName(tag)
                                .ifPresentOrElse(tagSet::add, () -> tagSet.add(new Tag(tag))));
                return tagSet;
            }

            @Override
            public String print(Set<Tag> tags, Locale locale) {
                return tags.stream().map(Tag::toString).collect(Collectors.joining(" "));
            }
        }, "tags");
    }

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
                .findFirst()).ifPresent(service -> model.addAttribute("questions", service.sort(pageable)));

        return LIST_TEMPLATE;
    }

    @GetMapping("/tagged/{tagName}")
    public String taggedQuestions(@PathVariable String tagName, Model model,
                                  @PageableDefault(
                                          sort = {"id"},
                                          direction = Sort.Direction.DESC,
                                          size = 5) Pageable pageable) {
        Tag tag = tagService.findByName(tagName).orElseThrow(() -> new TagNotFoundException(tagName));

        return LIST_TEMPLATE;
    }

    @GetMapping("/new")
    public String askQuestion(Model model) {
        model.addAttribute("question", new Question());

        return NEW_TEMPLATE;
    }

    @PostMapping
    public String saveQuestion(@Valid @ModelAttribute Question question, Principal principal) {
        String userEmail = principal.getName();
        Account author = accountService
                .findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User with this email not found: " + userEmail));

        question.setAuthor(author);
        questionService.save(question);

        return "redirect:questions";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, Principal principal) {
        Question question = questionService
                .findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        Answer answer = new Answer();
        answer.setQuestion(question);
        model.addAttribute("question", question);
        model.addAttribute("answer", answer);

        return VIEW_TEMPLATE;
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

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));

        model.addAttribute("question", question);

        return EDIT_TEMPLATE;
    }

    @PutMapping
    public String editQuestion(@Valid @ModelAttribute Question question) {
        Long updatedQuestionId = questionService
                .save(question)
                .getId();

        return String.format("redirect:%s/%d", QUESTIONS_PATH, updatedQuestionId);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteById(id);
        return "redirect:/questions";
    }

}
