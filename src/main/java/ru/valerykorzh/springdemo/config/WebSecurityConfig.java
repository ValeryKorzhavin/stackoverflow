package ru.valerykorzh.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.valerykorzh.springdemo.audit.AuditorAwareImpl;
import ru.valerykorzh.springdemo.controller.exception.CustomAccessDeniedHandler;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.service.AccountService;

import java.time.*;
import java.util.Optional;

import static ru.valerykorzh.springdemo.controller.ControllerConstants.*;

@Configuration
@EnableWebSecurity
//@EnableTransactionManagement
//@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")//, dateTimeProviderRef = "dateTimeProvider")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public AccountService accountService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<Account> auditorAware() {
        return new AuditorAwareImpl(accountService);
    }

//    @Bean
//    public DateTimeProvider dateTimeProvider() {
//        return () -> Optional.of(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(LOGIN_PATH, REGISTRATION_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(
                        "/resources/**",
                        "/public/**");
    }

}

