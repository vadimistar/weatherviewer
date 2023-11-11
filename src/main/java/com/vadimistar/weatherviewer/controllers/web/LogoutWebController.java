package com.vadimistar.weatherviewer.controllers.web;

import com.vadimistar.weatherviewer.factories.api.SessionDtoFactory;
import com.vadimistar.weatherviewer.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import static com.vadimistar.weatherviewer.utils.Utils.addSessionCookie;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class LogoutWebController {
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
