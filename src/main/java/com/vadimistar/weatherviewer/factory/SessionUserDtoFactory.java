package com.vadimistar.weatherviewer.factory;

import com.vadimistar.weatherviewer.dto.SessionUserDto;
import com.vadimistar.weatherviewer.entity.UserEntity;
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
