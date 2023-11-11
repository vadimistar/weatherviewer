package com.vadimistar.weatherviewer.utils;

import com.vadimistar.weatherviewer.dto.api.SessionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;

public class Utils {
    public static final String SESSION_ID = "sessionId";

    public static void addSessionCookie(
            HttpServletResponse response,
            SessionDto session) {
        Duration maxAge = Duration.between(Instant.now(), session.getExpiresAt());

        ResponseCookie cookie = ResponseCookie.from(SESSION_ID, session.getId())
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(maxAge)
                    .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
