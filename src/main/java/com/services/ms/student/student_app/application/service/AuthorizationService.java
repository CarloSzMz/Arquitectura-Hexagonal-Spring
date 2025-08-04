package com.services.ms.student.student_app.application.service;

import com.services.ms.student.student_app.application.ports.input.AuthorizationServicePort;
import com.services.ms.student.student_app.domain.exception.UnauthorizedException;
import com.services.ms.student.student_app.domain.model.Permission;
import com.services.ms.student.student_app.domain.model.Role;
import com.services.ms.student.student_app.domain.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements AuthorizationServicePort {

    @Override
    public boolean hasPermission(User user, String resource, String action) {
        if (user == null || user.getRoles() == null) {
            return false;
        }

        return user.getRoles().stream()
                .anyMatch(role -> roleHasPermission(role, resource, action));
    }

    @Override
    public boolean hasRole(User user, String roleName) {
        if (user == null || user.getRoles() == null) {
            return false;
        }

        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }

    @Override
    public boolean hasAnyRole(User user, String... roleNames) {
        if (user == null || user.getRoles() == null) {
            return false;
        }

        for (String roleName : roleNames) {
            if (hasRole(user, roleName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void checkPermission(User user, String resource, String action) {
        if (!hasPermission(user, resource, action)) {
            throw new UnauthorizedException(
                String.format("User does not have permission to %s on %s", action, resource)
            );
        }
    }

    @Override
    public void checkRole(User user, String roleName) {
        if (!hasRole(user, roleName)) {
            throw new UnauthorizedException(
                String.format("User does not have role: %s", roleName)
            );
        }
    }

    @Override
    public void checkAnyRole(User user, String... roleNames) {
        if (!hasAnyRole(user, roleNames)) {
            throw new UnauthorizedException(
                String.format("User does not have any of the required roles: %s", 
                    String.join(", ", roleNames))
            );
        }
    }

    private boolean roleHasPermission(Role role, String resource, String action) {
        if (role.getPermissions() == null) {
            return false;
        }

        return role.getPermissions().stream()
                .anyMatch(permission -> permissionMatches(permission, resource, action));
    }

    private boolean permissionMatches(Permission permission, String resource, String action) {
        return (permission.getResource().equals("*") || permission.getResource().equals(resource))
                && (permission.getAction().equals("*") || permission.getAction().equals(action));
    }
}