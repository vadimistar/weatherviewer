package com.vadimistar.weatherviewer;

import com.vadimistar.weatherviewer.dto.api.WeatherDto;
import com.vadimistar.weatherviewer.exceptions.OpenWeatherApiException;
import com.vadimistar.weatherviewer.services.WeatherService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneOffset;

import static com.vadimistar.weatherviewer.utils.Utils.createMockRestTemplateReturnsCode;
import static com.vadimistar.weatherviewer.utils.Utils.createMockRestTemplateReturnsJSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = WeatherviewerApplication.class
)
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherServiceTests {
    @Autowired
    WeatherService weatherService;

    private final static String HTTP_CLIENT_RESPONSE = "{\"coord\":{\"lon\":37.62,\"lat\":55.75},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":-3.39,\"feels_like\":-3.39,\"temp_min\":-4.83,\"temp_max\":-2.68,\"pressure\":1028,\"humidity\":47,\"sea_level\":1028,\"grnd_level\":1010},\"visibility\":10000,\"wind\":{\"speed\":0.83,\"deg\":104,\"gust\":0.88},\"clouds\":{\"all\":6},\"dt\":1700305923,\"sys\":{\"type\":2,\"id\":2000314,\"country\":\"RU\",\"sunrise\":1700284199,\"sunset\":1700313568},\"timezone\":10800,\"id\":524901,\"name\":\"Moscow\",\"cod\":200}";

    @Test
    public void weatherOk() {
        RestTemplate restTemplate = createMockRestTemplateReturnsJSON(HTTP_CLIENT_RESPONSE);

        WeatherDto weather = weatherService.getWeather(restTemplate, 20.0, 40.0);

        assertEquals(ZoneOffset.ofTotalSeconds(10800), weather.getZoneOffset());
        assertEquals("Clear", weather.getWeather());
        assertEquals(-3.39, weather.getTemperature());
        assertEquals("01d", weather.getIconUrl());
    }

    @Test
    public void weatherError() {
        RestTemplate restTemplate = createMockRestTemplateReturnsCode(HttpStatus.FORBIDDEN);

        assertThrows(OpenWeatherApiException.class,
                () -> weatherService.getWeather(restTemplate, 0.0, 0.0));
    }
}
