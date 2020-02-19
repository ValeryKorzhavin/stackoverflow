package ru.valerykorzh.springdemo.controller;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mapping.context.PersistentEntities;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Image;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.service.AccountService;
import ru.valerykorzh.springdemo.service.AnswerService;
import ru.valerykorzh.springdemo.service.QuestionService;

import java.time.LocalDateTime;
import java.util.Collections;

public abstract class AbstractControllerTest {

    protected static long EXISTED_USER_ID = 1L;

    protected static long EXISTED_QUESTION_ID = 1L;

    protected final String DEFAULT_USER_EMAIL = "test@gmail.com";

    protected final String DEFAULT_USER_NAME = "Test user";

    protected final String DEFAULT_USER_PASSWORD = "password";

    AuditingHandler auditingHandler;

    AuditorAware auditorAware;

    @MockBean
    protected AccountService accountService;

    @MockBean
    protected QuestionService questionService;

    @MockBean
    protected AnswerService answerService;

    protected AuditingHandler getAuditingHandler() {
        return new AuditingHandler(PersistentEntities.of());
    }

    protected Account createAccount(String name, String email) {
        return Account.builder()
                .name(name)
                .email(email)
                .password(DEFAULT_USER_PASSWORD)
                .avatar(new Image(""))
                .questions(Collections.emptyList())
                .answers(Collections.emptyList())
                .roles(Collections.emptySet())
                .build();
    }

    protected Account createAccountWithId(Long id, String name, String email) {
        Account account = createAccount(name, email);
        account.setId(id);
        return account;
    }

    protected Question createQuestion(Account account) {
        return Question.builder()
                .title("test question")
                .author(account)
                .body("text for test question")
                .answers(Collections.emptyList())
                .negativeVotes(Collections.emptySet())
                .positiveVotes(Collections.emptySet())
                .tags(Collections.emptySet())
                .build();
    }

    protected Question createQuestionWithId(Long id, Account account) {
        Question question = createQuestion(account);
        question.setId(id);

        return question;
    }

    protected void deleteAccount(final Long id) {
        accountService.deleteById(id);
    }

    protected void deleteQuestion(final Long id) {
        questionService.deleteById(id);
    }




}
