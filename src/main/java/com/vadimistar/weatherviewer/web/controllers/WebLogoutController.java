package com.vadimistar.weatherviewer.web.controllers;

import com.vadimistar.weatherviewer.api.factory.SessionDtoFactory;
import com.vadimistar.weatherviewer.api.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import static com.vadimistar.weatherviewer.api.utils.Utils.addSessionCookie;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class WebLogoutController {
    public static final String LOGOUT = "/logout";

    SessionService sessionService;

    SessionDtoFactory sessionDtoFactory;

    @GetMapping(LOGOUT)
    public String logout(HttpServletResponse response,
                       @CookieValue(defaultValue = "") String sessionId) {
        sessionService.removeSession(sessionId);

        addSessionCookie(response, sessionDtoFactory.createExpired());

        return "redirect:/";
    }
}
