package ru.valerykorzh.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Image;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.service.AccountService;
import ru.valerykorzh.springdemo.service.AnswerService;
import ru.valerykorzh.springdemo.service.QuestionService;

import java.util.Collections;

public abstract class AbstractControllerTest {

    protected final String DEFAULT_USER_NAME = "Test user";

    protected final String DEFAULT_USER_PASSWORD = "password";

    @MockBean
    protected AccountService accountService;

    @MockBean
    protected QuestionService questionService;

    @MockBean
    protected AnswerService answerService;

    protected Account createAccount(String name, String email) {
        return Account.builder()
                .name(name)
                .email(email)
                .password(DEFAULT_USER_PASSWORD)
                .avatar(new Image(""))
                .questions(Collections.emptyList())
                .answers(Collections.emptyList())
                .build();
    }

    protected Account createAccountWithId(Long id, String name, String email) {
        Account account = createAccount(name, email);
        account.setId(id);
        return account;
    }

    protected Question createQuestion(Account account) {
        Question question = new Question();
        question.setTitle("test question");
        question.setAuthor(account);
        question.setBody("text for test question");

        return questionService.save(question);
    }

    protected void deleteAccount(final Long id) {
        accountService.deleteById(id);
    }

    protected void deleteQuestion(final Long id) {
        questionService.deleteById(id);
    }




}
