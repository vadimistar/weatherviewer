package com.vadimistar.weatherviewer.factories.api;

import com.vadimistar.weatherviewer.config.OpenWeatherMapConfig;
import com.vadimistar.weatherviewer.dto.api.WeatherDto;
import com.vadimistar.weatherviewer.domain.api.WeatherApiResponse;
import com.vadimistar.weatherviewer.domain.api.WeatherApiResponseWeather;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
@AllArgsConstructor
public class WeatherDtoFactory {
    private final OpenWeatherMapConfig openWeatherMapConfig;

    public WeatherDto createWeatherDto(WeatherApiResponse response) {
        WeatherApiResponseWeather weather = response.getWeather()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("weather api not includes weather list"));

        String iconUrl = String.format(openWeatherMapConfig.getIconUrlFormat(), weather.getIcon());

        return WeatherDto.builder()
                .temperature(response.getMain().getTemp())
                .weather(weather.getMain())
                .iconUrl(iconUrl)
                .zoneOffset(ZoneOffset.ofTotalSeconds(response.getTimezone().intValue()))
                .build();
    }
}
