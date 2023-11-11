package com.vadimistar.weatherviewer.factories.web;

import com.vadimistar.weatherviewer.dto.api.FoundLocationDto;
import com.vadimistar.weatherviewer.dto.api.SavedLocationDto;
import com.vadimistar.weatherviewer.dto.api.WeatherDto;
import com.vadimistar.weatherviewer.domain.web.LocationModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class LocationModelFactory {
    public LocationModel createLocationModel(SavedLocationDto savedLocationDto) {
        return LocationModel.builder()
                .id(savedLocationDto.getId())
                .name(savedLocationDto.getName())
                .temperature(savedLocationDto.getTemperature().intValue())
                .weather(savedLocationDto.getWeather())
                .iconUrl(savedLocationDto.getIconUrl())
                .time(formatTime(savedLocationDto.getZoneOffset()))
                .lat(0.0)
                .lon(0.0)
                .build();
    }

    public LocationModel createLocationModel(FoundLocationDto location, WeatherDto weather) {
        return LocationModel.builder()
                .id(0L)
                .name(location.getName())
                .temperature(weather.getTemperature().intValue())
                .weather(weather.getWeather())
                .iconUrl(weather.getIconUrl())
                .time(formatTime(weather.getZoneOffset()))
                .lat(location.getLat())
                .lon(location.getLon())
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
