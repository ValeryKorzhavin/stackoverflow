package ru.valerykorzh.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valerykorzh.springdemo.domain.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByName(String name);

    Optional<Account> findOneByEmail(String email);

}
