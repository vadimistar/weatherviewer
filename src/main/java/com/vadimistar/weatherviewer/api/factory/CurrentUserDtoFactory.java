package com.vadimistar.weatherviewer.api.factory;

import com.vadimistar.weatherviewer.api.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.store.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserDtoFactory {
    public CurrentUserDto createCurrentUserDto(UserEntity user) {
        return CurrentUserDto
                .builder()
                .isLoggedIn(true)
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public CurrentUserDto createNotLoggedIn() {
        return CurrentUserDto
                .builder()
                .isLoggedIn(false)
                .id(-1L)
                .build();
    }
}
