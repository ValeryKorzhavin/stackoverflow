package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.valerykorzh.springdemo.dto.mapper.AccountMapper;
import ru.valerykorzh.springdemo.service.AccountService;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;


    @GetMapping("/accounts")
    public String findAll(Model model) {


        return "index";
    }

}
