package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.*;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.dto.QuestionDto;
import ru.valerykorzh.springdemo.service.TagService;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mappings({
        @Mapping(target = "tags", ignore = true),
        @Mapping(target = "answers", ignore = true),
        @Mapping(target = "author", ignore = true),
        @Mapping(target = "positiveVotes", ignore = true),
        @Mapping(target = "negativeVotes", ignore = true)
    })
    Question toQuestion(QuestionDto questionDto, @Context TagService tagService);

    @AfterMapping
    default void map(QuestionDto questionDto, @MappingTarget Question question, @Context TagService tagService) {
        Arrays.stream(questionDto.getTags().split("\\s+"))
                .forEach(tag -> tagService.findByName(tag)
                .ifPresentOrElse(question::addTag, () -> question.addTag(new Tag(tag))));
    }

}
