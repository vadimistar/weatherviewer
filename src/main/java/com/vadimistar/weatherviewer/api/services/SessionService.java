package com.vadimistar.weatherviewer.api.services;

import com.vadimistar.weatherviewer.api.dto.CurrentUserDto;
import com.vadimistar.weatherviewer.api.dto.SessionDto;
import com.vadimistar.weatherviewer.api.exceptions.BadRequestException;
import com.vadimistar.weatherviewer.api.factory.CurrentUserDtoFactory;
import com.vadimistar.weatherviewer.api.factory.SessionDtoFactory;
import com.vadimistar.weatherviewer.store.entity.SessionEntity;
import com.vadimistar.weatherviewer.store.entity.UserEntity;
import com.vadimistar.weatherviewer.store.repositories.SessionRepository;
import com.vadimistar.weatherviewer.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionService {
    public static final Duration SESSION_LIFETIME = Duration.ofDays(5);

    SessionRepository sessionRepository;
    CurrentUserDtoFactory currentUserDtoFactory;
    SessionDtoFactory sessionDtoFactory;
    UserRepository userRepository;

    public SessionDto createSession(String name, String password) {
        UserEntity user = userRepository.findByNameAndPassword(name, password)
                .orElseThrow(() -> new BadRequestException("invalid credentials"));

        SessionEntity session = sessionRepository.saveAndFlush(
                SessionEntity.builder()
                        .user(user)
                        .expiresAt(Instant.now().plus(SESSION_LIFETIME))
                        .build()
        );

        return sessionDtoFactory.createSessionDto(session);
    }

    public void removeSession(String sessionId) {
        sessionRepository.removeById(sessionId);
    }

    public CurrentUserDto getCurrentUser(String sessionId) {
        if (Objects.isNull(sessionId)) {
            return currentUserDtoFactory.createNotLoggedIn();
        }

        Optional<SessionEntity> session = sessionRepository.findById(sessionId);

        return session
                .map(SessionEntity::getUser)
                .map(currentUserDtoFactory::createCurrentUserDto)
                .orElse(currentUserDtoFactory.createNotLoggedIn());
    }
}
