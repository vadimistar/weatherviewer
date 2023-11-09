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

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RestController
@AllArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterController {
    public static final String REGISTER = "/api/register";

    UserRepository userRepository;
    SessionService sessionService;

    @PostMapping(REGISTER)
    public void register(HttpServletResponse response,
                                 @RequestParam String name,
                                 @RequestParam String password,
                                 @RequestParam(required = false) String redirectUrl) throws IOException {
        if (userRepository.existsByName(name)) {
            throw new BadRequestException("user with the specified name already exists");
        }

        UserEntity user = userRepository.saveAndFlush(
                UserEntity.builder()
                        .name(name)
                        .password(password)
                        .build()
        );

        sessionService.createSession(response, user);

        if (Objects.nonNull(redirectUrl)) {
            response.sendRedirect(redirectUrl);
        }
    }
}
