package com.vadimistar.weatherviewer.api.factory;

import com.vadimistar.weatherviewer.api.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.api.dto.SessionUserDto;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserDtoFactory {
    public CurrentUserDto createCurrentUserDto(SessionUserDto userDto) {
        return CurrentUserDto
                .builder()
                .isLoggedIn(true)
                .name(userDto.getName())
                .build();
    }

    public CurrentUserDto createNotLoggedIn() {
        return CurrentUserDto
                .builder()
                .isLoggedIn(false)
                .build();
    }
}
