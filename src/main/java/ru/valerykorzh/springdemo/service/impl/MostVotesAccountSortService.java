package ru.valerykorzh.springdemo.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.repository.AccountRepository;
import ru.valerykorzh.springdemo.service.AccountSortService;
import ru.valerykorzh.springdemo.service.AccountSortType;

@Service
@AllArgsConstructor
public class MostVotesAccountSortService implements AccountSortService {

    private final AccountRepository accountRepository;

    public Page<Account> sort(Pageable pageable) {
        Pageable unsortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return accountRepository.findAllSortByMostVotes(unsortedPageable);
    }

    public boolean isSuitableFor(AccountSortType sortType) {
        return AccountSortType.MOST_VOTES.equals(sortType);
    }

}
