package ru.valerykorzh.springdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.valerykorzh.springdemo.domain.Tag;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {

    private Long id;

    private String name;

    private String description;

    @JsonIgnoreProperties("tags")
    private Set<QuestionDto> questions;

}
