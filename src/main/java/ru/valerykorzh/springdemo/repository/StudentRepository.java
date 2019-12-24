package ru.valerykorzh.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valerykorzh.springdemo.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
