package ru.valerykorzh.springdemo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.service.impl.question.QuestionSortType;

public interface QuestionSortService {

    Page<Question> sort(Pageable pageable);

    boolean isSuitableFor(QuestionSortType sortType);

}
