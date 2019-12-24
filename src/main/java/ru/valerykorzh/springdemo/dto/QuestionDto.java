package ru.valerykorzh.springdemo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {

    private Long id;

    private String title;

    private String body;

    private Integer rating;

    private String tags;

}
