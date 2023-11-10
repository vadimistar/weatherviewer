package com.vadimistar.weatherviewer.api.factory;

import com.vadimistar.weatherviewer.api.dto.SessionDto;
import com.vadimistar.weatherviewer.store.entity.SessionEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SessionDtoFactory {
    public SessionDto createSessionDto(SessionEntity session) {
        return SessionDto.builder()
                .id(session.getId())
                .expiresAt(session.getExpiresAt())
                .build();
    }

    public SessionDto createExpired() {
        return SessionDto.builder()
                .id("")
                .expiresAt(Instant.now())
                .build();
    }
}
