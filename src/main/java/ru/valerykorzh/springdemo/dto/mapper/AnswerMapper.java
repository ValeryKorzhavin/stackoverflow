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

    @Mapping(target = "question", qualifiedByName = "questionToQuestionDto")
    AnswerDto toAnswerDto(Answer answer);

    @Named("questionToQuestionDto")
    QuestionDto toQuestionDto(Question question);

    List<AnswerDto> toAnswersDto(List<Answer> answers);

    List<Answer> toAnswers(List<AnswerDto> answersDto);
//    @Mappings({
//            @Mapping(target = "question", qualifiedByName = "questionDtoToQuestion"),
//    })
    Answer toAnswer(AnswerDto answerDto);

//    @Named("questionDtoToQuestion")
//    Question toQuestion(QuestionDto questionDto);

    default Set<Tag> map(String tags) {
        return Arrays
                .stream(tags.split("\\s+"))
                .map(tag -> new Tag())
                .collect(Collectors.toSet());
    }

    default String map(Set<Tag> tags) {
        return tags.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }

}
