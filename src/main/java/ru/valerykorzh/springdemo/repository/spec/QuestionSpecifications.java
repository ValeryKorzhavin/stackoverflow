package ru.valerykorzh.springdemo.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.valerykorzh.springdemo.domain.Question;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class QuestionSpecifications {

    public static Specification<Question> hasAcceptedAnswer() {
        return (root, query, criteriaBuilder) -> {
//            criteriaBuilder.isEmpty()
//            root.getModel().getAttribute("answers").
            return null;
        };
    }

    public static Specification<Question> hasNoAnswers() {
        return (root, query, criteriaBuilder) -> {
            return null;
        };
    }
}
