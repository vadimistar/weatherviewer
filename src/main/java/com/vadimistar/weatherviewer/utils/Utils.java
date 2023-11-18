package com.vadimistar.weatherviewer.utils;

import com.vadimistar.weatherviewer.dto.api.SessionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

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

    public static final String VALID_RANDOM_STRING_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890_";

    public static String generateRandomString(Random random, int length) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i ++) {
            stringBuilder.append(VALID_RANDOM_STRING_CHARACTERS.charAt(random.nextInt(VALID_RANDOM_STRING_CHARACTERS.length())));
        }

        return stringBuilder.toString();
    }

    public static RestTemplate createMockRestTemplateReturnsJSON(String response) {
        RestTemplate restTemplate = new RestTemplate();

        MockRestServiceServer.createServer(restTemplate)
                .expect(request -> {})
                .andRespond(request -> {
                    MockClientHttpResponse httpResponse = new MockClientHttpResponse(response.getBytes(StandardCharsets.UTF_8), HttpStatus.OK);
                    httpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return httpResponse;
                });

        return restTemplate;
    }

    public static RestTemplate createMockRestTemplateReturnsCode(HttpStatus code) {
        RestTemplate restTemplate = new RestTemplate();

        MockRestServiceServer.createServer(restTemplate)
                .expect(request -> {})
                .andRespond(request -> new MockClientHttpResponse("".getBytes(StandardCharsets.UTF_8), code));

        return restTemplate;
    }
}
