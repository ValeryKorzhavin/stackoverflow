package ru.valerykorzh.springdemo.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import ru.valerykorzh.springdemo.domain.Answer;
import ru.valerykorzh.springdemo.domain.Question;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Collection;

//
//@Query("SELECT q FROM Question q ORDER BY q.lastModifiedDate DESC")
//Page<Question> findAllSortByNewest(@Nullable Specification<Question> spec, Pageable pageable);
//
//@Query(
//        value = "SELECT * FROM question JOIN (SELECT q.id as question_id, GREATEST(q.last_modified_date, " +
//                "MAX(a.last_modified_date)) as recent_activity FROM question q LEFT JOIN answer a ON q.id" +
//                " = a.question_id GROUP BY q.id) as T ON question.id = T.question_id ORDER BY T.recent_activity DESC",
//        countQuery = "SELECT COUNT(*) FROM question",
//        nativeQuery = true
//)
//Page<Question> findAllSortByLastActive(@Nullable Specification<Question> spec, Pageable pageable);
//
//@Query("SELECT q FROM Question q ORDER BY q.positiveVotes.size - q.negativeVotes.size DESC")
//Page<Question> findAllSortByMostVotes(@Nullable Specification<Question> spec, Pageable pageable);

public class QuestionSpecifications {

    public static Specification<Question> sortByMostVotes() {
        return (root, query, cb) -> {


            return null;
        };
    }

    public static Specification<Question> sortByLastActive() {
        return (root, query, criteriaBuilder) -> {
//            ListJoin<Question, Answer> leftJoin = root.joinList("answers", JoinType.LEFT);
//            leftJoin.on(criteriaBuilder.equal(leftJoin.get("question_id"), root.get("id")));
//            query.groupBy(leftJoin.get("id"));
//            query.multiselect(leftJoin.get("id"), criteriaBuilder.max(leftJoin.get("last_modified_date")));
            Expression<Collection<Answer>> answers = root.get("answers");
//            criteriaBuilder.
            Subquery<Answer> subquery = query.subquery(Answer.class);
//            Root<Answer> answer = subquery.

//            subquery.select()
//            criteriaBuilder.any()
//            return query.where(criteriaBuilder.isNotEmpty(root.get("answers")), criteriaBuilder.any(subquery));
//            criteriaBuilder.isEmpty());
            return null;
        };
    }

    public static Specification<Question> sortByNewest() {
        return (root, query, criteriaBuilder) -> null;
    }

    public static Specification<Question> hasAcceptedAnswer() {
        return (root, query, criteriaBuilder) -> {



            return null;
        };
    }

    public static Specification<Question> hasNoAnswers() {
         return null;
    }
}
