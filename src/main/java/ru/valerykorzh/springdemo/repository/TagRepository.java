package ru.valerykorzh.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valerykorzh.springdemo.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}