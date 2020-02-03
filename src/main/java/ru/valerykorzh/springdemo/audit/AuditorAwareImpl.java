package ru.valerykorzh.springdemo.audit;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.service.AccountService;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Account> {

    private final AccountService accountService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<Account> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth) || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        String userEmail = ((UserDetails) auth.getPrincipal()).getUsername();
        return accountService.findByEmail(userEmail);
    }
}
