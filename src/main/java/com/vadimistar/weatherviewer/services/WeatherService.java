package com.vadimistar.weatherviewer.services;

import com.vadimistar.weatherviewer.config.OpenWeatherMapConfig;
import com.vadimistar.weatherviewer.dto.api.WeatherDto;
import com.vadimistar.weatherviewer.factories.api.WeatherDtoFactory;
import com.vadimistar.weatherviewer.domain.api.WeatherApiResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WeatherService {
    OpenWeatherMapConfig openWeatherMapConfig;
    WeatherDtoFactory weatherDtoFactory;

    public WeatherDto getWeather(double latitude, double longitude) {
        Map<String, Object> uriVariables = new HashMap<>();

        uriVariables.put("lat", latitude);
        uriVariables.put("lon", longitude);
        uriVariables.put("exclude", "hourly,daily");
        uriVariables.put("units", "metric");

        String apiUri = openWeatherMapConfig.getWeatherUri(uriVariables);
        WeatherApiResponse apiResponse = new RestTemplate().getForObject(apiUri, WeatherApiResponse.class);

        assert apiResponse != null;
        return weatherDtoFactory.createWeatherDto(apiResponse);
    }
}
