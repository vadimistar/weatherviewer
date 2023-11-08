package com.vadimistar.weatherviewer.repositories;

import com.vadimistar.weatherviewer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
