package com.vadimistar.weatherviewer.controllers;

import com.vadimistar.weatherviewer.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogoutController {
    public static final String PATH = "/api/logout";

    SessionService sessionService;

    @PostMapping(PATH)
    public void logout(HttpServletResponse response,
                       @CookieValue(defaultValue = "") String sessionId) {
        sessionService.removeSession(response, sessionId);
    }
}
