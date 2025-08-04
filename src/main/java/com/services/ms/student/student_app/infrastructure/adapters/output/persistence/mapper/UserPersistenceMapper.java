package com.services.ms.student.student_app.infrastructure.adapters.output.persistence.mapper;

import com.services.ms.student.student_app.domain.model.Permission;
import com.services.ms.student.student_app.domain.model.Role;
import com.services.ms.student.student_app.domain.model.User;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.entity.PermissionEntity;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.entity.RoleEntity;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.entity.UserEntity;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    User toUser(UserEntity userEntity);

    UserEntity toUserEntity(User user);

    List<User> toUserList(List<UserEntity> userEntities);

    List<UserEntity> toUserEntityList(List<User> users);

    Role toRole(RoleEntity roleEntity);

    RoleEntity toRoleEntity(Role role);

    Set<Role> toRoleSet(Set<RoleEntity> roleEntities);

    Set<RoleEntity> toRoleEntitySet(Set<Role> roles);

    Permission toPermission(PermissionEntity permissionEntity);

    PermissionEntity toPermissionEntity(Permission permission);

    Set<Permission> toPermissionSet(Set<PermissionEntity> permissionEntities);

    Set<PermissionEntity> toPermissionEntitySet(Set<Permission> permissions);
}