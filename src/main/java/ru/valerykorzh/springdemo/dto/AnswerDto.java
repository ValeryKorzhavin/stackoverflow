package ru.valerykorzh.springdemo.dto;

import lombok.*;
import ru.valerykorzh.springdemo.audit.Auditable;
import ru.valerykorzh.springdemo.domain.Account;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto extends Auditable<Account> {

    private Long id;

    private String content;

    private AccountDto author;

    private Boolean isAccepted;

    private Set<AccountDto> negativeVotes;

    private Set<AccountDto> positiveVotes;

    private QuestionDto question;

}
