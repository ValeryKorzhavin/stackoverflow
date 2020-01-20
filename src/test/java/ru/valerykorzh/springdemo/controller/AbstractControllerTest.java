package ru.valerykorzh.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.service.AccountService;
import ru.valerykorzh.springdemo.service.AnswerService;
import ru.valerykorzh.springdemo.service.QuestionService;

public abstract class AbstractControllerTest {

    protected final String DEFAULT_USER_NAME = "Test user";

    protected final String DEFAULT_USER_PASSWORD = "password";

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected QuestionService questionService;

    @Autowired
    protected AnswerService answerService;

    protected Account createAccount(String name, String email) {
        Account account = Account.builder()
                .name(name)
                .email(email)
                .password(DEFAULT_USER_PASSWORD)
                .build();

        return accountService.save(account);
    }

    protected Question createQuestion(Account account) {
        Question question = Question.builder()
                .title("test question")
                .body("text for test question")
                .author(account)
                .build();

        return questionService.save(question);
    }

    protected void deleteAccount(final Long id) {
        accountService.deleteById(id);
    }

    protected void deleteQuestion(final Long id) {
        questionService.deleteById(id);
    }




}
