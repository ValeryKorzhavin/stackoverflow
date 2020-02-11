package ru.valerykorzh.springdemo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.valerykorzh.springdemo.domain.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByName(String name);

    Optional<Account> findOneByEmail(String email);

    @Query(value = "select * from account a join account_role ac_roles on a.id = ac_roles.account_id where roles = ?1",
            countQuery = "select count(*) from account_role where roles = ?1",
            nativeQuery = true)
    Page<Account> findAllByRole(String role, Pageable pageable);

    @Query(value = "select * from account a left join (select T.id, sum(T.num) from (select a.id, count(*) as num" +
            " from account a join question_dislike dislikes on a.id = dislikes.account_id group by a.id union all " +
            "select a.id, count(*) as num from account a join question_like likes on a.id = likes.account_id group by" +
            " a.id union all select a.id, count(*) as num from account a join answer_like likes on a.id = " +
            "likes.account_id group by a.id union all select a.id, count(*) as num from account a join answer_dislike" +
            " dislikes on a.id = dislikes.account_id group by a.id) T group by T.id) U on a.id = U.id order by U.sum" +
            " desc nulls last",
            countQuery = "select count(*) from account",
            nativeQuery = true)
    Page<Account> findAllSortByMostVotes(Pageable pageable);

    @Query(value = "select * from account a left join (select U.id, sum(U.num) from (select u.id, count(*) as num " +
            "from account u join (select * from answer where created_date <> last_modified_date) a on u.id = " +
            "a.last_modified_by group by u.id union all select u.id, count(*) as num from account u join (select * " +
            "from question where created_date <> last_modified_date) q on u.id = q.last_modified_by group by u.id) U" +
            " group by U.id) T on a.id = T.id order by T.sum desc nulls last",
    countQuery = "select count(*) from account",
    nativeQuery = true)
    Page<Account> findAllSortByMostEdits(Pageable pageable);

}
