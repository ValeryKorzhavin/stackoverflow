package ru.valerykorzh.springdemo.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tag> findAllByMostPopular(Pageable pageable) {
        return tagRepository.findAllSortByMostPopular(pageable);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
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
