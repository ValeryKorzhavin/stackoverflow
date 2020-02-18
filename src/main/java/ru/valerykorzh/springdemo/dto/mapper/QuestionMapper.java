package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.*;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.dto.QuestionDto;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question toQuestion(QuestionDto questionDto);

    @Mappings({
        @Mapping(target = "author.questions", ignore = true),
        @Mapping(target = "author.answers", ignore = true),
    })
    QuestionDto toQuestionDto(Question question);

}
