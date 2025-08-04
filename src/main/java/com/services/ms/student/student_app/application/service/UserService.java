package com.services.ms.student.student_app.application.service;

import com.services.ms.student.student_app.application.ports.input.UserServicePort;
import com.services.ms.student.student_app.application.ports.output.UserPersistencePort;
import com.services.ms.student.student_app.domain.exception.UserNotFoundException;
import com.services.ms.student.student_app.domain.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(Long id) {
        return userPersistencePort.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findByUsername(String username) {
        return userPersistencePort.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findByEmail(String email) {
        return userPersistencePort.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        return userPersistencePort.findAll();
    }

    @Override
    public User save(User user) {
        // Encrypt password before saving
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }
        
        return userPersistencePort.save(user);
    }

    @Override
    public User update(Long id, User user) {
        return userPersistencePort.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setEnabled(user.isEnabled());
                    
                    // Only update password if provided
                    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    }
                    
                    if (user.getRoles() != null) {
                        existingUser.setRoles(user.getRoles());
                    }
                    
                    return userPersistencePort.save(existingUser);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if (userPersistencePort.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        userPersistencePort.deleteById(id);
    }

    @Override
    public User enableUser(Long id) {
        return userPersistencePort.findById(id)
                .map(user -> {
                    user.setEnabled(true);
                    return userPersistencePort.save(user);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User disableUser(Long id) {
        return userPersistencePort.findById(id)
                .map(user -> {
                    user.setEnabled(false);
                    return userPersistencePort.save(user);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userPersistencePort.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userPersistencePort.existsByEmail(email);
    }
}