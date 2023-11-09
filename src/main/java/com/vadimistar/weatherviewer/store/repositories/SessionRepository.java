package com.vadimistar.weatherviewer.store.repositories;

import com.vadimistar.weatherviewer.store.entity.SessionEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionEntity, String> {
    void removeById(@NonNull String id);
}
