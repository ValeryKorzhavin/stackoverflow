package ru.valerykorzh.springdemo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.valerykorzh.springdemo.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {

    Page<Question> findAll(Pageable pageable);

    @Query("SELECT q FROM Question q ORDER BY q.lastModifiedDate DESC")
    Page<Question> findAllSortByNewest(Pageable pageable);

    @Query(
        value = "SELECT * FROM question JOIN (SELECT q.id as question_id, GREATEST(q.last_modified_date, " +
                "MAX(a.last_modified_date)) as recent_activity FROM question q LEFT JOIN answer a ON q.id" +
                " = a.question_id GROUP BY q.id) as T ON question.id = T.question_id ORDER BY T.recent_activity DESC",
        countQuery = "SELECT COUNT(*) FROM question",
        nativeQuery = true
    )
    Page<Question> findAllSortByLastActive(Pageable pageable);

    @Query("SELECT q FROM Question q ORDER BY q.positiveVotes.size - q.negativeVotes.size DESC")
    Page<Question> findAllSortByMostVotes(Pageable pageable);

}
