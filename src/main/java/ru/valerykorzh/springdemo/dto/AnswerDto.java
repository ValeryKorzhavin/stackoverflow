package ru.valerykorzh.springdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.valerykorzh.springdemo.audit.Auditable;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Question;

import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = true)

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto extends Auditable<Account> {

    private Long id;

    private String content;

    private AccountDto author;

    private Boolean isAccepted;

//    @JsonIgnoreProperties("answers")
    private Set<AccountDto> negativeVotes;

//    @JsonIgnoreProperties("answers")
    private Set<AccountDto> positiveVotes;

    @JsonIgnoreProperties("answers")
    private QuestionDto question;

}
