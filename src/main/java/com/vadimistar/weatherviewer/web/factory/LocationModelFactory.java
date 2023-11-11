package com.vadimistar.weatherviewer.web.factory;

import com.vadimistar.weatherviewer.api.dto.FoundLocationDto;
import com.vadimistar.weatherviewer.api.dto.SavedLocationDto;
import com.vadimistar.weatherviewer.api.dto.WeatherDto;
import com.vadimistar.weatherviewer.web.models.LocationModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class LocationModelFactory {
    public LocationModel createLocationModel(SavedLocationDto savedLocationDto) {
        return LocationModel.builder()
                .name(savedLocationDto.getName())
                .temperature(savedLocationDto.getTemperature().intValue())
                .weather(savedLocationDto.getWeather())
                .iconUrl(savedLocationDto.getIconUrl())
                .time(formatTime(savedLocationDto.getZoneOffset()))
                .build();
    }

    public LocationModel createLocationModel(FoundLocationDto location, WeatherDto weather) {
        return LocationModel.builder()
                .name(location.getName())
                .temperature(weather.getTemperature().intValue())
                .weather(weather.getWeather())
                .iconUrl(weather.getIconUrl())
                .time(formatTime(weather.getZoneOffset()))
                .build();
    }

    private String formatTime(ZoneOffset zoneOffset) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.now(),
                zoneOffset
        );

        return localDateTime.format(formatter);
    }
}
