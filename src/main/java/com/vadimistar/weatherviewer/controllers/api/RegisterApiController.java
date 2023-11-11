package com.vadimistar.weatherviewer.controllers.api;

import com.vadimistar.weatherviewer.dto.api.SessionDto;
import com.vadimistar.weatherviewer.services.UserService;
import com.vadimistar.weatherviewer.services.SessionService;
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

import static com.vadimistar.weatherviewer.utils.Utils.addSessionCookie;

@RestController
@AllArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterApiController {
    public static final String REGISTER = "/api/register";

    UserService userService;
    SessionService sessionService;

    @PostMapping(REGISTER)
    public void register(HttpServletResponse response,
                                 @RequestParam String name,
                                 @RequestParam String password,
                                 @RequestParam(required = false) String redirectUrl) throws IOException {
        userService.createUser(name, password);

        SessionDto session = sessionService.createSession(name, password);

        addSessionCookie(response, session);

        if (Objects.nonNull(redirectUrl)) {
            response.sendRedirect(redirectUrl);
        }
    }
}
