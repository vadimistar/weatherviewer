package com.vadimistar.weatherviewer.api.factory;

import com.vadimistar.weatherviewer.api.dto.WeatherDto;
import com.vadimistar.weatherviewer.api.model.WeatherApiResponse;
import com.vadimistar.weatherviewer.api.model.WeatherApiResponseWeather;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneOffset;

@Component
public class WeatherDtoFactory {
    public WeatherDto createWeatherDto(WeatherApiResponse response) {
        WeatherApiResponseWeather weather = response.getWeather()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("weather api not includes weather list"));

        String iconUrl = String.format("https://openweathermap.org/img/wn/%s@4x.png", weather.getIcon());

        return WeatherDto.builder()
                .time(Instant.ofEpochSecond(response.getDt()))
                .temperature(response.getMain().getTemp())
                .weather(weather.getMain())
                .iconUrl(iconUrl)
                .zoneOffset(ZoneOffset.ofTotalSeconds(response.getTimezone().intValue()))
                .build();
    }
}
