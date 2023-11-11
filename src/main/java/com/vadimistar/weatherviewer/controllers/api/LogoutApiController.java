package com.vadimistar.weatherviewer.controllers.api;

import com.vadimistar.weatherviewer.factories.api.SessionDtoFactory;
import com.vadimistar.weatherviewer.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import static com.vadimistar.weatherviewer.utils.Utils.addSessionCookie;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class LogoutApiController {
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
