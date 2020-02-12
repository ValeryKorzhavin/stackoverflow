package ru.valerykorzh.springdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.valerykorzh.springdemo.audit.Auditable;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Question;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto extends Auditable<Account> {

    private Long id;

    private String content;

//    private Question question;

}
