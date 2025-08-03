package com.services.ms.student.student_app.infrastructure.adapters.input.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.services.ms.student.student_app.domain.model.Student;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.request.StudentCreateRequest;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response.StudentResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentRestMapper {

    //@Mapping(target = "id", ignore = true)
    //Ya se ignora el id porque se indica con el unmappedTargetPolicy
    Student toStudent(StudentCreateRequest request);

    StudentResponse toStudentResponse(Student student);

    List<StudentResponse> toStudentResponseList(List<Student> students);
}
