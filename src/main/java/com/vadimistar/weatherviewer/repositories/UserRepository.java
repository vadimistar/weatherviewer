package com.vadimistar.weatherviewer.repositories;

import com.vadimistar.weatherviewer.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByName(@NonNull String name);
    Optional<UserEntity> findByNameAndPassword(@NonNull String name, @NonNull String password);
}
