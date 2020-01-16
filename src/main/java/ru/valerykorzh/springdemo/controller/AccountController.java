package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.dto.mapper.AccountMapper;
import ru.valerykorzh.springdemo.service.AccountService;

import java.util.List;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("/accounts")
    public String findAll(Model model) {

        List<Account> accounts = accountService.findAll();

        model.addAttribute("accounts", accounts);

        return "account/list";
    }

    @GetMapping("/accounts/{id}")
    public String viewAccount(@PathVariable Long id, Model model) {
        Account account = accountService.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("User with this id=%d not found", id)));

        model.addAttribute("account", account);

        return "account/view";
    }

    @PutMapping("/accounts")
    public String updateAccount(@RequestBody Account account) {

        accountService.save(account);
        Long id = account.getId();
        return String.format("redirect:/accounts/%d", id);
    }

}
