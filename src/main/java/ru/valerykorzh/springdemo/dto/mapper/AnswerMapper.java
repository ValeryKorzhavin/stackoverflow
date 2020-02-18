package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.valerykorzh.springdemo.domain.Answer;
import ru.valerykorzh.springdemo.dto.AnswerDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mappings({
            @Mapping(target = "question.answers", ignore = true),
            @Mapping(target = "question.author", ignore = true),
            @Mapping(target = "author.answers", ignore = true),
            @Mapping(target = "author.questions", ignore = true),
    })
    AnswerDto toAnswerDto(Answer answer);

    List<AnswerDto> toAnswersDto(List<Answer> answers);

    List<Answer> toAnswers(List<AnswerDto> answersDto);

    Answer toAnswer(AnswerDto answerDto);

}
