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

    @Query(value = "select * from account join (select u.id, count(*) as edits_count from account u join question q on u.id = q.last_modified_by" +
            " join answer a on u.id = a.last_modified_by group by u.id) as T on account.id = T.id order by T.edits_count",
            nativeQuery = true)
    Page<Account> findAllSortByNewest(Pageable pageable);

    @Query(value = "select * from account join (select u.id, count(*) as edits_count from account u join question q on u.id = q.last_modified_by" +
            " join answer a on u.id = a.last_modified_by group by u.id) as T on account.id = T.id order by T.edits_count",
            nativeQuery = true)
    Page<Account> findAllSortByMostVotes(Pageable pageable);

    // at least 5 posts edited by
    @Query(value = "select * from account join (select u.id, count(*) as edits_count from account u join question q on u.id = q.last_modified_by" +
            " join answer a on u.id = a.last_modified_by group by u.id) as T on account.id = T.id order by T.edits_count",
    nativeQuery = true)
    Page<Account> findAllSortByMostEdits(Pageable pageable);

}
