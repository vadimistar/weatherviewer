package com.vadimistar.weatherviewer.api.factory;

import com.vadimistar.weatherviewer.api.dto.SessionUserDto;
import com.vadimistar.weatherviewer.store.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SessionUserDtoFactory {
    public SessionUserDto createSessionUserDto(UserEntity entity) {
        return SessionUserDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
