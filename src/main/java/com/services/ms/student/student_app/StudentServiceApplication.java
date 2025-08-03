package com.services.ms.student.student_app;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.entity.StudentEntity;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class StudentServiceApplication implements CommandLineRunner {

	private final StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<StudentEntity> entities = Arrays.asList(
				new StudentEntity(null, "Juan", "Sanz", 22, "calle 123"),
				new StudentEntity(null, "Juan", "Sanz", 22, "calle 123"),
				new StudentEntity(null, "Juan", "Sanz", 22, "calle 123"));

		repository.saveAll(entities);
	}

}
