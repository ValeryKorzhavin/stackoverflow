package ru.valerykorzh.springdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.valerykorzh.springdemo.domain.Question;
import ru.valerykorzh.springdemo.domain.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    private Long id;

    @NotBlank(message = "Name can't be empty")
    @Size(message = "Name can't be more 15 characters", max = 15)
    private String name;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Invalid email")
    private String email;

    private ImageDto avatar;

    private Set<Role> roles;

    private List<AnswerDto> answers;

    private List<QuestionDto> questions;



}
