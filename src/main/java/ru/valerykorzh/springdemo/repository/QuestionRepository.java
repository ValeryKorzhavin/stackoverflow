package ru.valerykorzh.springdemo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valerykorzh.springdemo.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAll(Pageable pageable);

}
