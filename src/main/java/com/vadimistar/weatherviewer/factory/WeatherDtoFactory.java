package com.vadimistar.weatherviewer.factory;

import com.vadimistar.weatherviewer.dto.WeatherDto;
import com.vadimistar.weatherviewer.model.WeatherApiResponse;
import com.vadimistar.weatherviewer.model.WeatherApiResponseWeather;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class WeatherDtoFactory {
    public WeatherDto createWeatherDto(WeatherApiResponse response) {
        WeatherApiResponseWeather weather = response.getWeather()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("weather api not includes weather list"));

        return WeatherDto.builder()
                .time(Instant.ofEpochSecond(response.getDt()))
                .temperature(response.getMain().getTemp())
                .weather(weather.getMain())
                .icon(weather.getIcon())
                .build();
    }
}
