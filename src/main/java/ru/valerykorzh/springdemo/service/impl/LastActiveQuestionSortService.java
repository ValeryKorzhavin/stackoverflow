package ru.valerykorzh.springdemo.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.repository.QuestionRepository;
import ru.valerykorzh.springdemo.service.QuestionSortType;
import ru.valerykorzh.springdemo.service.QuestionSortService;

@Service
@AllArgsConstructor
public class LastActiveQuestionSortService implements QuestionSortService {

    private final QuestionRepository questionRepository;

    @Override
    public Page<Question> sort(Pageable pageable) {
        Pageable unsortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return questionRepository.findAllSortByLastActive(unsortedPageable);
    }

    @Override
    public boolean isSuitableFor(QuestionSortType sortType) {
        return QuestionSortType.LAST_ACTIVE.equals(sortType);
    }

}
