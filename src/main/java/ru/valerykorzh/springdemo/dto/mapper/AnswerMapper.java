package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.valerykorzh.springdemo.domain.Answer;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.dto.AnswerDto;
import ru.valerykorzh.springdemo.dto.QuestionDto;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
