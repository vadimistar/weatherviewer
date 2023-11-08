package com.vadimistar.weatherviewer.factory;

import com.vadimistar.weatherviewer.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserDtoFactory {
    public CurrentUserDto createCurrentUserDto(UserEntity entity) {
        return CurrentUserDto
                .builder()
                .isLoggedIn(true)
                .name(entity.getName())
                .build();
    }

    public CurrentUserDto createNotLoggedIn() {
        return CurrentUserDto
                .builder()
                .isLoggedIn(false)
                .build();
    }
}
