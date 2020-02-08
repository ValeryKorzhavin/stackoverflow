package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.service.TagService;

@Controller
@AllArgsConstructor
public class TagController {

    private final TagService tagService;

    @ModelAttribute("module")
    public String module() {
        return "tags";
    }

    @GetMapping("/tags")
    public String findAll(Model model,
                          @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Tag> tags = tagService.findAll(pageable);

        model.addAttribute("tags", tags);

        return "tag/list";
    }


}
