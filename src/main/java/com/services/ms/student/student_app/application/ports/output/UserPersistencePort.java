package com.services.ms.student.student_app.application.ports.output;

import com.services.ms.student.student_app.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {

    Optional<User> findById(Long id);
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    List<User> findAll();
    
    User save(User user);
    
    void deleteById(Long id);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}