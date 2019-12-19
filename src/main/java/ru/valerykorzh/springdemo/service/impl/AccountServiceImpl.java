package ru.valerykorzh.springdemo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.repository.AccountRepository;
import ru.valerykorzh.springdemo.service.AccountService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findOneByEmail(email);
    }

    @Override
    public List<Account> findByName(String name) {
        return accountRepository.findByName(name);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
//         this code invokes while registration processing
        if (account.getId() != null) {
            Account accountToPut = accountRepository.findById(account.getId()).orElseThrow();
            accountToPut.setEmail(account.getEmail());
            accountToPut.setName(account.getName());
            return accountRepository.save(accountToPut);
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));

        return accountRepository.save(account);
    }

    @Override
    public void deleteById(Long id) {
        try {
            accountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            log.info("Delete non existing entity with id=" + id, ex);
        }
    }
}
