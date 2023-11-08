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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import static com.vadimistar.weatherviewer.controllers.RegisterController.SESSION_LIFETIME;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {
    public static final String PATH = "/api/login";
    public static final String SESSION_ID_COOKIE = "session_id";

    SessionRepository sessionRepository;
    UserRepository userRepository;

    @PostMapping(PATH)
    public void login(HttpServletResponse response,
                      @RequestParam String name,
                      @RequestParam String password,
                      @RequestParam(required = false) String redirectUrl) throws IOException {
        Optional<UserEntity> user = userRepository.findByNameAndPassword(name, password);

        if (!user.isPresent()) {
            throw new BadRequestException("Invalid username or password");
        }

        SessionEntity session = sessionRepository.saveAndFlush(
                    SessionEntity.builder()
                        .user(user.get())
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
