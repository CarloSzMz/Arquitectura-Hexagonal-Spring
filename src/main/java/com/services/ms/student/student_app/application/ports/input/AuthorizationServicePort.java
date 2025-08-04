package com.services.ms.student.student_app.application.ports.input;

import com.services.ms.student.student_app.domain.model.User;

public interface AuthorizationServicePort {

    boolean hasPermission(User user, String resource, String action);
    
    boolean hasRole(User user, String roleName);
    
    boolean hasAnyRole(User user, String... roleNames);
    
    void checkPermission(User user, String resource, String action);
    
    void checkRole(User user, String roleName);
    
    void checkAnyRole(User user, String... roleNames);
}