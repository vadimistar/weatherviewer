package com.vadimistar.weatherviewer.repositories;

import com.vadimistar.weatherviewer.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<SessionEntity, String> {
}
