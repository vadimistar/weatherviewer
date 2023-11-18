package com.vadimistar.weatherviewer.services;

import com.vadimistar.weatherviewer.config.SessionsConfig;
import com.vadimistar.weatherviewer.dto.api.CurrentUserDto;
import com.vadimistar.weatherviewer.dto.api.SessionDto;
import com.vadimistar.weatherviewer.exceptions.InvalidCredentialsException;
import com.vadimistar.weatherviewer.factories.api.CurrentUserDtoFactory;
import com.vadimistar.weatherviewer.factories.api.SessionDtoFactory;
import com.vadimistar.weatherviewer.store.entity.SessionEntity;
import com.vadimistar.weatherviewer.store.entity.UserEntity;
import com.vadimistar.weatherviewer.store.repositories.SessionRepository;
import com.vadimistar.weatherviewer.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionService {
    SessionRepository sessionRepository;
    CurrentUserDtoFactory currentUserDtoFactory;
    SessionDtoFactory sessionDtoFactory;
    UserRepository userRepository;
    SessionsConfig sessionsConfig;

    public SessionDto createSession(String name, String password) {
        UserEntity user = userRepository.findByNameAndPassword(name, password)
                .orElseThrow(InvalidCredentialsException::new);

        SessionEntity session = sessionRepository.saveAndFlush(
                SessionEntity.builder()
                        .user(user)
                        .expiresAt(Instant.now().plus(sessionsConfig.getLifetime()))
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

        if (!session.isPresent()) {
            return currentUserDtoFactory.createNotLoggedIn();
        }

        if (session.get().getExpiresAt().isBefore(Instant.now())) {
            return currentUserDtoFactory.createNotLoggedIn();
        }

        return currentUserDtoFactory.createCurrentUserDto(session.get().getUser());
    }
}
