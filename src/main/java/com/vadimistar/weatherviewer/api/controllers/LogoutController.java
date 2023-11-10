package com.vadimistar.weatherviewer.api.controllers;

import com.vadimistar.weatherviewer.api.factory.SessionDtoFactory;
import com.vadimistar.weatherviewer.api.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import static com.vadimistar.weatherviewer.api.utils.Utils.addSessionCookie;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class LogoutController {
    public static final String LOGOUT = "/api/logout";

    SessionService sessionService;

    SessionDtoFactory sessionDtoFactory;

    @PostMapping(LOGOUT)
    public void logout(HttpServletResponse response,
                       @CookieValue(defaultValue = "") String sessionId) {
        sessionService.removeSession(sessionId);

        addSessionCookie(response, sessionDtoFactory.createExpired());
    }
}
