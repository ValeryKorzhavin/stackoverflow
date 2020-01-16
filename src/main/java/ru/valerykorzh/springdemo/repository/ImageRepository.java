package ru.valerykorzh.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.valerykorzh.springdemo.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
