package com.services.ms.student.student_app.application.ports.input;

import com.services.ms.student.student_app.domain.model.User;
import java.util.List;

public interface UserServicePort {

    User findById(Long id);
    
    User findByUsername(String username);
    
    User findByEmail(String email);
    
    List<User> findAll();
    
    User save(User user);
    
    User update(Long id, User user);
    
    void deleteById(Long id);
    
    User enableUser(Long id);
    
    User disableUser(Long id);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}