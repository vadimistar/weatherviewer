package com.vadimistar.weatherviewer.controllers;

import com.vadimistar.weatherviewer.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.entity.SessionEntity;
import com.vadimistar.weatherviewer.factory.CurrentUserDtoFactory;
import com.vadimistar.weatherviewer.repositories.SessionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@RestController
public class CurrentUserController {
    public static final String FETCH_CURRENT_USER = "/api/current_user";

    SessionRepository sessionRepository;
    CurrentUserDtoFactory currentUserDtoFactory;

    @GetMapping(FETCH_CURRENT_USER)
    public CurrentUserDto fetchCurrentUser(@CookieValue(defaultValue = "") String sessionId)  {
        Optional<SessionEntity> session = sessionRepository.findById(sessionId);

        return session
                .map(SessionEntity::getUser)
                .map(currentUserDtoFactory::createCurrentUserDto)
                .orElseGet(currentUserDtoFactory::createNotLoggedIn);
    }
}
