package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.*;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.dto.AccountDto;
import ru.valerykorzh.springdemo.dto.AccountPostDto;
import ru.valerykorzh.springdemo.dto.QuestionDto;

import java.beans.ConstructorProperties;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "questions", qualifiedByName = "questionsToQuestionDto")
    AccountDto toAccountDto(Account account);

    @Named("questionsToQuestionsDto")
    @Mapping(target = "author", expression = "java(null)")
    QuestionDto toQuestionDto(Question question);

    @Mappings({
            @Mapping(target = "questions", ignore = true),
            @Mapping(target = "password", ignore = true)
    })
    Account toAccount(AccountDto accountDto);

    @Mapping(target = "id", ignore = true)
    Account postDtoToAccount(AccountPostDto accountPostDto);

}
