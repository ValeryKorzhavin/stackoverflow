package ru.valerykorzh.springdemo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.service.impl.account.AccountSortType;

public interface AccountSortService {

    Page<Account> sort(Pageable pageable);

    boolean isSuitableFor(AccountSortType sortType);

}
