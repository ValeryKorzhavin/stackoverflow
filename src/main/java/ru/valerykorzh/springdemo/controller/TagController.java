package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.service.TagService;

import java.util.List;

@Controller
@AllArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/tags")
    public String findAll(Model model) {
        List<Tag> tags = tagService.findAll();

        model.addAttribute("tags", tags);

        return "tag/list";
    }


}
