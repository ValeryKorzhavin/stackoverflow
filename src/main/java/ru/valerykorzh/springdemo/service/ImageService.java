package ru.valerykorzh.springdemo.service;

import ru.valerykorzh.springdemo.domain.Image;

import java.util.Optional;

public interface ImageService {

    Optional<Image> findById(Long id);

    Image save(Image image);

    void deleteById(Long id);

}
