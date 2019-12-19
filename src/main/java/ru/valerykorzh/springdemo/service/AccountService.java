package ru.valerykorzh.springdemo.service;

import ru.valerykorzh.springdemo.domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> findById(Long id);

    Optional<Account> findByEmail(String email);

    // cause users may have same names
    List<Account> findByName(String name);

    List<Account> findAll();

    Account save(Account account);

    void deleteById(Long id);

}
