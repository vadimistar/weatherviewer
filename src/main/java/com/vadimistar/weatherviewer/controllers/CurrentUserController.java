package com.vadimistar.weatherviewer.controllers;

import com.vadimistar.weatherviewer.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.factory.CurrentUserDtoFactory;
import com.vadimistar.weatherviewer.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@RestController
public class CurrentUserController {
    public static final String FETCH_CURRENT_USER = "/api/current_user";

    SessionService sessionService;
    CurrentUserDtoFactory currentUserDtoFactory;

    @GetMapping(FETCH_CURRENT_USER)
    public CurrentUserDto fetchCurrentUser(@CookieValue(defaultValue = "") String sessionId)  {
        return sessionService.getUserBySession(sessionId)
                .map(currentUserDtoFactory::createCurrentUserDto)
                .orElseGet(currentUserDtoFactory::createNotLoggedIn);
    }
}
