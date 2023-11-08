package com.vadimistar.weatherviewer.repositories;

import com.vadimistar.weatherviewer.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByName(@NonNull String name);
}
