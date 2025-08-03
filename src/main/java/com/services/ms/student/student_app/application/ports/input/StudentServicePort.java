package com.services.ms.student.student_app.application.ports.input;

import java.util.List;

import com.services.ms.student.student_app.domain.model.Student;

public interface StudentServicePort {

    Student findById(Long id);

    List<Student> findAll();

    Student save(Student student);

    Student update(Long id, Student student);

    void deleteById(Long id);

    
}
