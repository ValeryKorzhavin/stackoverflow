package ru.valerykorzh.springdemo.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.valerykorzh.springdemo.domain.Image;
import ru.valerykorzh.springdemo.repository.ImageRepository;
import ru.valerykorzh.springdemo.service.ImageService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteById(Long id) {
        imageRepository.deleteById(id);
    }

}
