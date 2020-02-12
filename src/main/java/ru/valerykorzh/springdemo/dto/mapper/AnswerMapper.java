package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.Mapper;
import ru.valerykorzh.springdemo.domain.Answer;
import ru.valerykorzh.springdemo.dto.AnswerDto;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerDto toAnswerDto(Answer answer);

    Answer toAnswer(AnswerDto answerDto);

}
