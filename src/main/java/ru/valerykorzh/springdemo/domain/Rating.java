package ru.valerykorzh.springdemo.domain;

import javax.persistence.*;
import java.util.List;

public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "positive")
    private Integer positive;

    @Column(name = "negative")
    private Integer negative;

    @ManyToMany
    private List<Account> author;

}
