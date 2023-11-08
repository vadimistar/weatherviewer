package com.vadimistar.weatherviewer.services;

import com.vadimistar.weatherviewer.dto.SessionUserDto;
import com.vadimistar.weatherviewer.entity.SessionEntity;
import com.vadimistar.weatherviewer.entity.UserEntity;
import com.vadimistar.weatherviewer.factory.SessionUserDtoFactory;
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
import java.util.Optional;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionService {
    public static final Duration SESSION_LIFETIME = Duration.ofDays(5);
    private static final Long SESSION_EXPIRED = 0L;
    public static final String SESSION_ID_COOKIE = "sessionId";

    SessionRepository sessionRepository;
    SessionUserDtoFactory sessionUserDtoFactory;

    public void createSession(HttpServletResponse response, UserEntity user) throws IOException {
        SessionEntity session = sessionRepository.saveAndFlush(
                SessionEntity.builder()
                        .user(user)
                        .expiresAt(Instant.now().plus(SESSION_LIFETIME))
                        .build()
        );

        ResponseCookie responseCookie = createResponseCookie(session.getId(), SESSION_LIFETIME.getSeconds());

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    public void removeSession(HttpServletResponse response, String sessionId) {
        sessionRepository.removeById(sessionId);

        ResponseCookie responseCookie = createResponseCookie(sessionId, SESSION_EXPIRED);

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    public Optional<SessionUserDto> getUserBySession(String sessionId) {
        Optional<SessionEntity> session = sessionRepository.findById(sessionId);

        return session
                .map(SessionEntity::getUser)
                .map(sessionUserDtoFactory::createSessionUserDto);
    }

    private ResponseCookie createResponseCookie(String sessionId, long maxAgeSeconds) {
        return ResponseCookie.from(SESSION_ID_COOKIE, sessionId)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(maxAgeSeconds)
                .build();
    }
}
