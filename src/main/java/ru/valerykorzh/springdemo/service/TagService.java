package ru.valerykorzh.springdemo.service;

import ru.valerykorzh.springdemo.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    List<Tag> findAll();

    Optional<Tag> findById(Long id);

    Tag save(Tag tag);

    void deleteById(Long id);

}
