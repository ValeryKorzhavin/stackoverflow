package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Answer;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.dto.AccountDto;
import ru.valerykorzh.springdemo.dto.AnswerDto;
import ru.valerykorzh.springdemo.dto.QuestionDto;
import ru.valerykorzh.springdemo.service.TagService;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question toQuestion(QuestionDto questionDto);

    @Mappings({
        @Mapping(target = "author.questions", ignore = true),
        @Mapping(target = "author.answers", ignore = true),
    })
    QuestionDto toQuestionDto(Question question);

}
