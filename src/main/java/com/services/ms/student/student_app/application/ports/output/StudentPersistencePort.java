package com.services.ms.student.student_app.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.services.ms.student.student_app.domain.model.Student;

public interface StudentPersistencePort {


    Optional<Student> findById(Long id);
    
    List<Student> findAll();

    Student save(Student student);

    void deleteById(Long id);
 
}
