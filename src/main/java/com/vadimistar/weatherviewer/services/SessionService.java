package com.vadimistar.weatherviewer.services;

import com.password4j.Password;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@EnableScheduling
@Component
@AllArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionService {
    SessionRepository sessionRepository;
    CurrentUserDtoFactory currentUserDtoFactory;
    SessionDtoFactory sessionDtoFactory;
    UserRepository userRepository;
    SessionsConfig sessionsConfig;

    public SessionDto createSession(String name, String password) {
        UserEntity user = userRepository.findByName(name)
                .orElseThrow(InvalidCredentialsException::new);

        SessionEntity session = sessionRepository.saveAndFlush(
                SessionEntity.builder()
                        .user(user)
                        .expiresAt(Instant.now().plus(sessionsConfig.getLifetime()))
                        .build()
        );

        String actualPassword = user.getPassword();

        if (!Password.check(password, actualPassword).withBcrypt()) {
            throw new InvalidCredentialsException();
        }

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

    @Scheduled(cron = "0 * * * * *")
    public void removeInvalidSessions() {
        sessionRepository.removeAllByExpiresAtBefore(Instant.now());
    }
}
