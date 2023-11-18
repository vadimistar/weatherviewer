package com.vadimistar.weatherviewer;

import com.vadimistar.weatherviewer.exceptions.UserAlreadyExistsException;
import com.vadimistar.weatherviewer.services.UserService;
import com.vadimistar.weatherviewer.store.entity.UserEntity;
import com.vadimistar.weatherviewer.store.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

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
public class UserServiceTests {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void createUserOk() {
        Random random = new Random(1);

        String name = generateRandomString(random, 10);
        String password = generateRandomString(random, 10);

        userService.createUser(name, password);

        Optional<UserEntity> got = userRepository.findByNameAndPassword(name, password);

        assertTrue(got.isPresent());
        assertEquals(got.get().getName(), name);
        assertEquals(got.get().getPassword(), password);
    }

    @Test
    public void createUserAlreadyExists() {
        Random random = new Random(2);

        String name = generateRandomString(random, 10);
        String password = generateRandomString(random, 10);

        userService.createUser(name, password);
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(name, password));
    }
}
