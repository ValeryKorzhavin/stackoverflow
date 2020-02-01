package ru.valerykorzh.springdemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.valerykorzh.springdemo.controller.exception.AccountNotFoundException;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.security.UserPrincipal;
import ru.valerykorzh.springdemo.service.AccountService;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(email).orElseThrow(() -> new AccountNotFoundException(email));

        UserDetails userDetails = User.builder()
            .username(account.getEmail())
            .password(account.getPassword())
            .roles(account
                .getRoles()
                .stream()
                .map(Enum::toString)
                .toArray(String[]::new))
            .build();

        return new UserPrincipal(userDetails, account.getId());
    }

}
