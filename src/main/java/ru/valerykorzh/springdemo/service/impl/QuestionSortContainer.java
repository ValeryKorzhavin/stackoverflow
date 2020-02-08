package ru.valerykorzh.springdemo.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.service.QuestionSortService;
import ru.valerykorzh.springdemo.service.QuestionSortType;

import java.util.List;

@Component
@AllArgsConstructor
public class QuestionSortContainer {

    private List<QuestionSortService> questionSortServices;

    Page<Question> sort(QuestionSortType sortType) {
        return null;
    }


}
