package com.vadimistar.weatherviewer.api.controllers;

import com.vadimistar.weatherviewer.store.entity.UserEntity;
import com.vadimistar.weatherviewer.api.exceptions.BadRequestException;
import com.vadimistar.weatherviewer.store.repositories.UserRepository;
import com.vadimistar.weatherviewer.api.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {
    public static final String LOGIN = "/api/login";

    UserRepository userRepository;
    SessionService sessionService;

    @PostMapping(LOGIN)
    public void login(HttpServletResponse response,
                      @RequestParam String name,
                      @RequestParam String password,
                      @RequestParam(required = false) String redirectUrl) throws IOException {
        UserEntity user = userRepository
                .findByNameAndPassword(name, password)
                .orElseThrow(() -> new BadRequestException("invalid username or password"));

        sessionService.createSession(response, user);

        if (redirectUrl != null) {
            response.sendRedirect(redirectUrl);
        }
    }
}