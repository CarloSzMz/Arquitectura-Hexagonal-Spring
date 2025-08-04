package com.services.ms.student.student_app.infrastructure.adapters.output;

import com.services.ms.student.student_app.application.ports.output.UserPersistencePort;
import com.services.ms.student.student_app.domain.model.User;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id)
                .map(userPersistenceMapper::toUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userPersistenceMapper::toUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userPersistenceMapper::toUser);
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> entities = userJpaRepository.findAll();
        return userPersistenceMapper.toUserList(entities);
    }

    @Override
    public User save(User user) {
        UserEntity entity = userPersistenceMapper.toUserEntity(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return userPersistenceMapper.toUser(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}