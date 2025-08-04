package com.services.ms.student.student_app.infrastructure.config;

import com.services.ms.student.student_app.application.ports.input.UserServicePort;
import com.services.ms.student.student_app.domain.model.Role;
import com.services.ms.student.student_app.domain.model.Permission;
import com.services.ms.student.student_app.domain.model.User;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2) // Execute after data.sql
public class DataInitializer implements CommandLineRunner {

    private final UserServicePort userServicePort;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if users already exist to avoid duplicates
        if (userJpaRepository.count() > 0) {
            log.info("Users already exist, skipping initialization");
            return;
        }

        log.info("Initializing users with proper password encryption...");

        // Create admin user
        User admin = User.builder()
                .username("admin")
                .email("admin@example.com")
                .password("password") // Will be encrypted by UserService
                .enabled(true)
                .roles(createAdminRoles())
                .build();

        // Create regular user  
        User user = User.builder()
                .username("user")
                .email("user@example.com")
                .password("password") // Will be encrypted by UserService
                .enabled(true)
                .roles(createUserRoles())
                .build();

        try {
            userServicePort.save(admin);
            userServicePort.save(user);
            log.info("Users created successfully with encrypted passwords");
        } catch (Exception e) {
            log.error("Error creating users: {}", e.getMessage());
        }
    }

    private Set<Role> createAdminRoles() {
        Permission studentRead = Permission.builder().id(1L).name("STUDENT_READ").build();
        Permission studentWrite = Permission.builder().id(2L).name("STUDENT_WRITE").build();
        Permission studentDelete = Permission.builder().id(3L).name("STUDENT_DELETE").build();
        Permission userAdmin = Permission.builder().id(4L).name("USER_ADMIN").build();

        Role adminRole = Role.builder()
                .id(2L)
                .name("ADMIN")
                .permissions(Set.of(studentRead, studentWrite, studentDelete, userAdmin))
                .build();

        return Set.of(adminRole);
    }

    private Set<Role> createUserRoles() {
        Permission studentRead = Permission.builder().id(1L).name("STUDENT_READ").build();

        Role userRole = Role.builder()
                .id(1L)
                .name("USER")
                .permissions(Set.of(studentRead))
                .build();

        return Set.of(userRole);
    }
}