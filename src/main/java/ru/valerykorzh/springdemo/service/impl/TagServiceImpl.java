package ru.valerykorzh.springdemo.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.repository.TagRepository;
import ru.valerykorzh.springdemo.service.TagService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;


    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag save(Tag tag) {
//        if (tagRepository.findByName(tag.getName()).isEmpty()) {
            return tagRepository.save(tag);
//        }
//        return tag;
    }

    @Override
    public void deleteById(Long id) {
        try {
            tagRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            log.info("Tag id not found: " + id, ex);
        }
    }
}
