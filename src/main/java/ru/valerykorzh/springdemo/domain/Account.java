package ru.valerykorzh.springdemo.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name can't be empty")
    @Size(message = "Name can't be more 15 characters", max = 15)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Invalid email")
    @NotBlank(message = "Email can't be empty")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password can't be empty")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id")
    private Image avatar;

    @OneToMany(mappedBy = "author", cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Question> questions;

    @OneToMany(mappedBy = "author", cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Answer> answers;

}
