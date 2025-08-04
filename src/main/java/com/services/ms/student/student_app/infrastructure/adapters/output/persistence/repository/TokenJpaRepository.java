package com.services.ms.student.student_app.infrastructure.adapters.output.persistence.repository;

import com.services.ms.student.student_app.infrastructure.adapters.output.persistence.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByTokenAndValidTrue(String token);
    
    Optional<TokenEntity> findByUsernameAndValidTrue(String username);
    
    @Modifying
    @Query("UPDATE TokenEntity t SET t.valid = false WHERE t.username = :username")
    void invalidateAllUserTokens(@Param("username") String username);
    
    @Modifying
    @Query("UPDATE TokenEntity t SET t.valid = false WHERE t.token = :token")
    void invalidateToken(@Param("token") String token);
    
    @Modifying
    @Query("DELETE FROM TokenEntity t WHERE t.expiresAt < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);
}