package ru.valerykorzh.springdemo.service.impl.account;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Role;
import ru.valerykorzh.springdemo.repository.AccountRepository;
import ru.valerykorzh.springdemo.service.AccountSortService;

@Service
@AllArgsConstructor
public class RoleAccountSortService implements AccountSortService {

    private final AccountRepository accountRepository;

    @Override
    public Page<Account> sort(Pageable pageable) {
        Pageable unsortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return accountRepository.findAllByRole(Role.MODERATOR.toString(), unsortedPageable);
    }

    @Override
    public boolean isSuitableFor(AccountSortType sortType) {
        return AccountSortType.MODERATORS.equals(sortType);
    }
}
