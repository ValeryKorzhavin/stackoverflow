package ru.valerykorzh.springdemo.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.repository.QuestionRepository;
import ru.valerykorzh.springdemo.service.QuestionSortService;
import ru.valerykorzh.springdemo.service.QuestionSortType;

@Service
@AllArgsConstructor
public class IdQuestionSortService implements QuestionSortService {

    private final QuestionRepository questionRepository;

    @Override
    public Page<Question> sort(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public boolean isSuitableFor(QuestionSortType sortType) {
        return QuestionSortType.ID.equals(sortType);
    }
}
