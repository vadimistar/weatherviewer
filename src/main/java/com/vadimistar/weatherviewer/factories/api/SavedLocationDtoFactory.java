package com.vadimistar.weatherviewer.factories.api;

import com.vadimistar.weatherviewer.dto.api.SavedLocationDto;
import com.vadimistar.weatherviewer.dto.api.WeatherDto;
import com.vadimistar.weatherviewer.store.entity.LocationEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SavedLocationDtoFactory {
    public SavedLocationDto createLocationDto(LocationEntity location, WeatherDto weather) {
        return SavedLocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .weather(weather.getWeather())
                .temperature(BigDecimal.valueOf(weather.getTemperature()))
                .iconUrl(weather.getIconUrl())
                .zoneOffset(weather.getZoneOffset())
                .build();
    }
}
