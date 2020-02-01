package ru.valerykorzh.springdemo.domain;

import lombok.*;
import ru.valerykorzh.springdemo.repository.TagRepository;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body", columnDefinition = "text", length = 65536, nullable = false)
    @NotBlank(message = "Question body can't be empty")
    private String body;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "author_id")
    private Account author;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "question_tag",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    public void addTag(Tag tag) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        tags.add(tag);
//        tag.addQuestion(this);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

}
