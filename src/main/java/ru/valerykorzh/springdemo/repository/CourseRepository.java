package ru.valerykorzh.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valerykorzh.springdemo.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
