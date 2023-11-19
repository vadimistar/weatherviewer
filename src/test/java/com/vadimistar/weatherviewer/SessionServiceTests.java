package com.vadimistar.weatherviewer;

import com.vadimistar.weatherviewer.config.SessionsConfig;
import com.vadimistar.weatherviewer.dto.api.CurrentUserDto;
import com.vadimistar.weatherviewer.dto.api.SessionDto;
import com.vadimistar.weatherviewer.exceptions.InvalidCredentialsException;
import com.vadimistar.weatherviewer.services.SessionService;
import com.vadimistar.weatherviewer.services.UserService;
import com.vadimistar.weatherviewer.store.entity.SessionEntity;
import com.vadimistar.weatherviewer.store.entity.UserEntity;
import com.vadimistar.weatherviewer.store.repositories.SessionRepository;
import com.vadimistar.weatherviewer.store.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

import static com.vadimistar.weatherviewer.utils.Utils.generateRandomString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = WeatherviewerApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@Transactional
public class SessionServiceTests {
    @Autowired
    SessionService sessionService;
    @Autowired
    UserService userService;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionsConfig sessionsConfig;

    @Test
    public void createSessionOk() {
        Random random = new Random(3);

        String name = generateRandomString(random, 10);
        String password = generateRandomString(random, 10);

        userService.createUser(name, password);
        Optional<UserEntity> user = userRepository.findByName(name);
        assertTrue(user.isPresent());

        SessionDto session = sessionService.createSession(name, password);

        Optional<SessionEntity> got = sessionRepository.findById(session.getId());

        assertTrue(got.isPresent());
        assertEquals(user.get().getId(), got.get().getUser().getId());
        assertEquals(session.getId(), got.get().getId());
        assertEquals(session.getExpiresAt(), got.get().getExpiresAt());
    }

    @Test
    public void createSessionUserNotExists() {
        Random random = new Random(4);

        String name = generateRandomString(random, 10);
        String password = generateRandomString(random, 10);

        assertThrows(InvalidCredentialsException.class, () -> sessionService.createSession(name, password));
    }

    @Test
    public void removeSessionOk() {
        Random random = new Random(5);

        String name = generateRandomString(random, 10);
        String password = generateRandomString(random, 10);

        userService.createUser(name, password);

        SessionDto session = sessionService.createSession(name, password);

        sessionService.removeSession(session.getId());
    }

    @Test
    public void sessionExpiration() {
        Random random = new Random(7);

        String name = generateRandomString(random, 10);
        String password = generateRandomString(random, 10);

        userService.createUser(name, password);

        SessionDto session = sessionService.createSession(name, password);

        CurrentUserDto currentUser = sessionService.getCurrentUser(session.getId());

        assertEquals(currentUser.getIsLoggedIn(), false);
    }
}
