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

    @JsonIgnoreProperties("questions")
    private Set<Tag> tags;

    @JsonIgnoreProperties({"questions", "answers"})
    private Account author;

    private Set<Account> positiveVotes;

    private Set<Account> negativeVotes;

//    @NotNull(message = "Account NOT NULL")
//    @JsonIgnoreProperties("questions")
//    private AccountDto author;

    @JsonIgnoreProperties("question")
    private List<AnswerDto> answers;

    public Integer getRating() {
        if (positiveVotes != null && negativeVotes != null) {
            return positiveVotes.size() - negativeVotes.size();
        }
        return 0;
    }

}
