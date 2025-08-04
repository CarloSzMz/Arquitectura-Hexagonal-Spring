package com.services.ms.student.student_app.infrastructure.adapters.input.rest.mapper;

import com.services.ms.student.student_app.domain.model.AuthToken;
import com.services.ms.student.student_app.domain.model.Permission;
import com.services.ms.student.student_app.domain.model.Role;
import com.services.ms.student.student_app.domain.model.User;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response.AuthResponse;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response.PermissionResponse;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response.RoleResponse;
import com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.response.UserResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuthRestMapper {

    @Mapping(target = "user", source = "user")
    AuthResponse toAuthResponse(AuthToken authToken);

    UserResponse toUserResponse(User user);

    Set<UserResponse> toUserResponseSet(Set<User> users);

    RoleResponse toRoleResponse(Role role);

    Set<RoleResponse> toRoleResponseSet(Set<Role> roles);

    PermissionResponse toPermissionResponse(Permission permission);

    Set<PermissionResponse> toPermissionResponseSet(Set<Permission> permissions);
}