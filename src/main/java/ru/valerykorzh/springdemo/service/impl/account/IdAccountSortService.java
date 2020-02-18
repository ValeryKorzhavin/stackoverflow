package ru.valerykorzh.springdemo.service.impl.account;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.repository.AccountRepository;
import ru.valerykorzh.springdemo.service.AccountSortService;

@Service
@AllArgsConstructor
public class IdAccountSortService implements AccountSortService {

    private final AccountRepository accountRepository;

    @Override
    public Page<Account> sort(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public boolean isSuitableFor(AccountSortType sortType) {
        return AccountSortType.ID.equals(sortType);
    }
}
