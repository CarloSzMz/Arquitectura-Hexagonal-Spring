package com.services.ms.student.student_app.infrastructure.adapters.input.rest;

import com.services.ms.student.student_app.application.ports.input.UserServicePort;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.mapper.AuthRestMapper;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.request.UserCreateRequest;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response.UserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestAdapter {

    private final UserServicePort userServicePort;
    private final AuthRestMapper authRestMapper;

    @GetMapping("/v1/api")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> findAll() {
        return userServicePort.findAll().stream()
                .map(authRestMapper::toUserResponse)
                .toList();
    }

    @GetMapping("/v1/api/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse findById(@PathVariable Long id) {
        return authRestMapper.toUserResponse(userServicePort.findById(id));
    }

    @PostMapping("/v1/api")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> save(@Valid @RequestBody UserCreateRequest request) {
        // This would need a proper mapper implementation to handle role assignment
        // For now, creating a basic user without roles
        var user = com.services.ms.student.student_app.domain.model.User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .enabled(request.isEnabled())
                .build();
        
        var savedUser = userServicePort.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authRestMapper.toUserResponse(savedUser));
    }

    @PutMapping("/v1/api/{id}/enable")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse enableUser(@PathVariable Long id) {
        return authRestMapper.toUserResponse(userServicePort.enableUser(id));
    }

    @PutMapping("/v1/api/{id}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse disableUser(@PathVariable Long id) {
        return authRestMapper.toUserResponse(userServicePort.disableUser(id));
    }

    @DeleteMapping("/v1/api/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        userServicePort.deleteById(id);
    }
}