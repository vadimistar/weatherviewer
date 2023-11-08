package com.vadimistar.weatherviewer.controllers;

import com.vadimistar.weatherviewer.entity.SessionEntity;
import com.vadimistar.weatherviewer.entity.UserEntity;
import com.vadimistar.weatherviewer.exceptions.BadRequestException;
import com.vadimistar.weatherviewer.repositories.SessionRepository;
import com.vadimistar.weatherviewer.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterController {
    public static final String PATH = "/api/register";
    public static final Duration SESSION_LIFETIME = Duration.ofDays(5);
    public static final String SESSION_ID_COOKIE = "session_id";

    UserRepository userRepository;
    SessionRepository sessionRepository;

    @PostMapping(PATH)
    public void register(HttpServletResponse response,
                                 @RequestParam String name,
                                 @RequestParam String password,
                                 @RequestParam(required = false) String redirectUrl) throws IOException {
        boolean userExists = userRepository.existsByName(name);

        if (userExists) {
            throw new BadRequestException("user with the specified name already exists");
        }

        UserEntity user = userRepository.saveAndFlush(
                UserEntity.builder()
                        .name(name)
                        .password(password)
                        .build()
        );

        SessionEntity session = sessionRepository.saveAndFlush(
                SessionEntity.builder()
                        .user(user)
                        .expiresAt(Instant.now().plus(SESSION_LIFETIME))
                        .build()
        );

        ResponseCookie sessionCookie = ResponseCookie.from(SESSION_ID_COOKIE, session.getId())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(SESSION_LIFETIME)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, sessionCookie.toString());

        if (redirectUrl != null) {
            response.sendRedirect(redirectUrl);
        }
    }
}
