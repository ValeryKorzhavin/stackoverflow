package ru.valerykorzh.springdemo.dto;

import lombok.*;
import ru.valerykorzh.springdemo.audit.Auditable;
import ru.valerykorzh.springdemo.domain.Account;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto extends Auditable<Account> {

    private Long id;

    private String title;

    private String body;

    private Set<TagDto> tags;

    private Set<AccountDto> positiveVotes;

    private Set<AccountDto> negativeVotes;

    @NotNull(message = "Account NOT NULL")
    private AccountDto author;

    private List<AnswerDto> answers;

}
