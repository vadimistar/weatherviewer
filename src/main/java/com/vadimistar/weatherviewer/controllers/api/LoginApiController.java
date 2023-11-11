package com.vadimistar.weatherviewer.controllers.api;

import com.vadimistar.weatherviewer.dto.api.SessionDto;
import com.vadimistar.weatherviewer.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.vadimistar.weatherviewer.utils.Utils.addSessionCookie;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginApiController {
    public static final String LOGIN = "/api/login";

    SessionService sessionService;

    @PostMapping(LOGIN)
    public void login(HttpServletResponse response,
                      @RequestParam String name,
                      @RequestParam String password,
                      @RequestParam(required = false) String redirectUri) throws IOException {
        SessionDto session = sessionService.createSession(name, password);

        addSessionCookie(response, session);

        if (Objects.nonNull(redirectUri)) {
            response.sendRedirect(redirectUri);
        }
    }
}
