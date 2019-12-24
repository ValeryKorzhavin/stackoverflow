package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.valerykorzh.springdemo.domain.*;
import ru.valerykorzh.springdemo.dto.QuestionDto;
import ru.valerykorzh.springdemo.dto.mapper.QuestionMapper;
import ru.valerykorzh.springdemo.repository.CourseRepository;
import ru.valerykorzh.springdemo.repository.StudentRepository;
import ru.valerykorzh.springdemo.service.AccountService;
import ru.valerykorzh.springdemo.service.QuestionService;
import ru.valerykorzh.springdemo.service.TagService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
//        question.getTags().forEach(tagService::save);

//        Student student1 = new Student("john", "smith", "john@gmail.com");
//        Student student2 = new Student("sarah", "johnson", "sarah@gmail.com");
//        Course course1 = new Course("Math");
//        Course course2 = new Course("Physics");
//        course1.addStudent(student1);
//        course2.addStudent(student2);
//        student1.addCourse(course1);
//        student2.addCourse(course2);
//        courseRepository.save(course1);
//        courseRepository.save(course2);
//        studentRepository.save(student1);
//        studentRepository.save(student2);

        return "redirect:questions";
    }

    @GetMapping("/questions/{questionId}")
    public String findById(@PathVariable Long questionId) {
        Question question = questionService
                .findById(questionId)
                .orElseThrow(() -> new RuntimeException("questionId not exists"));

        return "question/view";
    }

}
