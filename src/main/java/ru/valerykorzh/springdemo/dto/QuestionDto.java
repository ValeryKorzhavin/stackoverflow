package ru.valerykorzh.springdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.valerykorzh.springdemo.audit.Auditable;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Tag;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)

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
