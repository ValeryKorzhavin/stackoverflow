package ru.valerykorzh.springdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.valerykorzh.springdemo.controller.exception.AccountNotFoundException;
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

import static ru.valerykorzh.springdemo.controller.ControllerConstants.ACCOUNTS_PATH;

@Controller
@RequestMapping(ACCOUNTS_PATH)
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ImageService imageService;
    private final AccountMapper accountMapper;

    @ModelAttribute("module")
    public String module() {
        return "accounts";
    }

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 20) Pageable pageable) {

        List<Account> accounts = accountService.findAll();

        model.addAttribute("accounts", accounts);

        return "account/list";
    }

    @GetMapping("/{id}")
    public String viewAccount(@PathVariable Long id, Model model) {
        Account account = accountService.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        model.addAttribute("account", account);

        return "account/view";
    }

    @PutMapping
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

    @GetMapping("/edit/{id}")
    @PreAuthorize("authentication.principal.id.equals(#id)") // or hasRole('ROLE_ADMIN')
    public String getEditAccountForm(@PathVariable Long id, Model model) {

        Account account = accountService
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        model.addAttribute("account", account);

        return "account/edit";
    }

    @PostMapping("/new")
    public String createAccount() {

        return "/account/list";
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);
        return String.format("redirect:%s", ACCOUNTS_PATH);
    }

}
