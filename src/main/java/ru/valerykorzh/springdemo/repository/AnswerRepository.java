package ru.valerykorzh.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valerykorzh.springdemo.domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
