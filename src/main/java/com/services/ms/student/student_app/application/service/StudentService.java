package com.services.ms.student.student_app.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.services.ms.student.student_app.application.ports.input.StudentServicePort;
import com.services.ms.student.student_app.application.ports.output.StudentPersistencePort;
import com.services.ms.student.student_app.domain.exception.StudentNotFoundException;
import com.services.ms.student.student_app.domain.model.Student;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentServicePort{

    private final StudentPersistencePort persistencePort;

    @Override
    public Student findById(Long id) {
        return persistencePort.findById(id)
        .orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public List<Student> findAll() {
        return persistencePort.findAll();    
    }

    @Override
    public Student save(Student student) {
        return persistencePort.save(student);
    }

    @Override
    public Student update(Long id, Student student) {
        return persistencePort.findById(id)
        .map(savedStudent -> {
            savedStudent.setFirstName(student.getFirstName());
            savedStudent.setLastName(student.getLastName());
            savedStudent.setAge(student.getAge());
            savedStudent.setAddress(student.getAddress());
            return persistencePort.save(savedStudent);
        })
        .orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
      if (persistencePort.findById(id).isEmpty()) {
        throw new StudentNotFoundException();
      }
      persistencePort.deleteById(id);
    }
    
}
