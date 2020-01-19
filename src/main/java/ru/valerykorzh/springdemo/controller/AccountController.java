package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Image;
import ru.valerykorzh.springdemo.dto.AccountDto;
import ru.valerykorzh.springdemo.dto.mapper.AccountMapper;
import ru.valerykorzh.springdemo.service.AccountService;
import ru.valerykorzh.springdemo.service.ImageService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ImageService imageService;
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

    @PutMapping(value = "/accounts")
    public String updateAccount(@ModelAttribute Account account, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            imageService.findById(account.getAvatar().getId())
                .ifPresent(image -> {
                    try {
                        image.setData(Base64.getEncoder().encodeToString(file.getBytes()));
                        imageService.save(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }

        accountService.save(account);
        Long id = account.getId();
        return String.format("redirect:/accounts/%d", id);
    }

    @GetMapping("/accounts/edit/{id}")
    public String getEditAccountForm(@PathVariable Long id, Model model) {

        Account account = accountService.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("User with this id not found: id=%d", id)));

//        Runnable error = () -> model.addAttribute("error", "Error occurred");
//        Consumer<Account> fillModel = account -> model.addAttribute("account", account);
//        accountService.findById(id).ifPresentOrElse(fillModel, error);

        model.addAttribute("account", account);

        return "account/edit";
    }

}
