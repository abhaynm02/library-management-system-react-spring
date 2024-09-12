package com.abhaynm.library_management.repository;

import com.abhaynm.library_management.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String username);
    boolean existsByEmail(String email);
}
