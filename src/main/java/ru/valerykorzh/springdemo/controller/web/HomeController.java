package ru.valerykorzh.springdemo.controller.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.service.QuestionService;
import ru.valerykorzh.springdemo.service.TagService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final QuestionService questionService;
    private final TagService tagService;

    @ModelAttribute("module")
    public String module() {
        return "index";
    }

    @GetMapping
    public String index(Model model) {
        List<Question> questions = questionService.findAll();

        int tagsCount = 20;
        List<Tag> tags = tagService
                .findAll()
                .stream()
                .limit(tagsCount)
                .collect(Collectors.toList());

        model.addAttribute("tags", tags);
        model.addAttribute("questions", questions);

        return "index";
    }

}
