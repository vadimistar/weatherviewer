package com.vadimistar.weatherviewer.services;

import com.vadimistar.weatherviewer.entity.SessionEntity;
import com.vadimistar.weatherviewer.entity.UserEntity;
import com.vadimistar.weatherviewer.repositories.SessionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionService {
    public static final Duration SESSION_LIFETIME = Duration.ofDays(5);
    private static final Long SESSION_EXPIRED = -1L;
    public static final String SESSION_ID_COOKIE = "session_id";

    SessionRepository sessionRepository;

    public void createSession(HttpServletResponse response, UserEntity user) throws IOException {
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
    }

    public void removeSession(HttpServletResponse response, String sessionId) {
        sessionRepository.removeById(sessionId);

        ResponseCookie sessionCookie = ResponseCookie.from(SESSION_ID_COOKIE, sessionId)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(SESSION_EXPIRED)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, sessionCookie.toString());
    }
}
