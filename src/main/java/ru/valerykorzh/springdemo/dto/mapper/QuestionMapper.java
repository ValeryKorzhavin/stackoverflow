package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.*;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.dto.AccountDto;
import ru.valerykorzh.springdemo.dto.QuestionDto;
import ru.valerykorzh.springdemo.service.TagService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    default Question toQuestion(QuestionDto questionDto, @Context TagService tagService) {
        if (questionDto == null) {
            return null;
        }

        Question question = new Question();
        question.setId(questionDto.getId());
        question.setTitle(questionDto.getTitle());
        question.setBody(questionDto.getBody());
        question.setAuthor(questionDto.getAuthor());

        if (questionDto.getNegativeVotes() != null) {
            question.setNegativeVotes(new HashSet<Account>(questionDto.getNegativeVotes()));
        }
        if (questionDto.getPositiveVotes() != null) {
            question.setPositiveVotes(new HashSet<Account>(questionDto.getPositiveVotes()));
        }
        Arrays.stream(questionDto.getTags().split("\\s+"))
            .forEach(tag -> tagService.findByName(tag)
            .ifPresentOrElse(question::addTag, () -> question.addTag(new Tag(tag))));

        return question;
    }

    default QuestionDto toQuestionDto(Question question) {
        if (question == null) {
            return null;
        }

        QuestionDto questionDto = new QuestionDto();
        questionDto.setAuthor(question.getAuthor());
        questionDto.setBody(question.getBody());
        questionDto.setId(question.getId());
        questionDto.setTitle(question.getTitle());

        if (question.getNegativeVotes() != null) {
            questionDto.setNegativeVotes(new HashSet<Account>(question.getNegativeVotes()));
        }
        if (question.getPositiveVotes() != null) {
            questionDto.setPositiveVotes(new HashSet<Account>(question.getPositiveVotes()));
        }
        if (question.getTags() != null) {
            questionDto.setTags(question
                .getTags()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(" ")));
        }

        return questionDto;
    }

}
