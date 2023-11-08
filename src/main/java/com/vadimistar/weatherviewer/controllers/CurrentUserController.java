package com.vadimistar.weatherviewer.controllers;

import com.vadimistar.weatherviewer.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.entity.SessionEntity;
import com.vadimistar.weatherviewer.factory.CurrentUserDtoFactory;
import com.vadimistar.weatherviewer.repositories.SessionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@RestController
public class CurrentUserController {
    public static final String FETCH_CURRENT_USER = "/api/current_user";

    SessionRepository sessionRepository;
    CurrentUserDtoFactory currentUserDtoFactory;

    @GetMapping(FETCH_CURRENT_USER)
    public ResponseEntity<CurrentUserDto> fetchCurrentUser(@CookieValue(required = false) String sessionId)  {
        CurrentUserDto currentUserDto = sessionRepository
                .findById(UUID.fromString(sessionId))
                .map(SessionEntity::getUser)
                .map(currentUserDtoFactory::createCurrentUserDto)
                .orElseGet(currentUserDtoFactory::createNotLoggedIn);

        return ResponseEntity.ok(currentUserDto);
    }
}
