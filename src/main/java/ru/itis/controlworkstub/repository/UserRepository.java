package ru.itis.controlworkstub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.controlworkstub.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}