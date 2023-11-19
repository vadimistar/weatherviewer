package com.vadimistar.weatherviewer.store.repositories;

import com.vadimistar.weatherviewer.store.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByName(@NonNull String name);
    Optional<UserEntity> findByName(@NonNull String name);
}
