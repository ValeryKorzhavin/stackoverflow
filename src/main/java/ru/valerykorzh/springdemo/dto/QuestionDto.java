package ru.valerykorzh.springdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.valerykorzh.springdemo.audit.Auditable;
import ru.valerykorzh.springdemo.domain.Account;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto extends Auditable<Account> {

    private Long id;

    private String title;

    private String body;

    private Integer rating;

    private String tags;

    private Account author;

    private Set<Account> positiveVotes;

    private Set<Account> negativeVotes;

//    @NotNull(message = "Account NOT NULL")
//    @JsonIgnoreProperties("questions")
//    private AccountDto author;

}
