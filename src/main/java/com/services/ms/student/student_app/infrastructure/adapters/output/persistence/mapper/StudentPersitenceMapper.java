package com.services.ms.student.student_app.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.services.ms.student.student_app.domain.model.Student;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.entity.StudentEntity;

@Mapper(componentModel = "spring")
public interface StudentPersitenceMapper {

    StudentEntity toStudentEntity(Student student);

    Student toStudent(StudentEntity studentEntity);

    List<Student> toStudentList(List<StudentEntity> studentEntities);

}
