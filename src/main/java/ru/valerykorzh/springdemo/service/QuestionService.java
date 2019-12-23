package ru.valerykorzh.springdemo.service;

import ru.valerykorzh.springdemo.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    List<Question> findAll();

    Optional<Question> findById(Long id);

    Question save(Question question);

    void deleteById(Long id);

}
