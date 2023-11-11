package com.vadimistar.weatherviewer.api.factory;

import com.vadimistar.weatherviewer.api.dto.SavedLocationDto;
import com.vadimistar.weatherviewer.api.dto.WeatherDto;
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
                .time(weather.getTime())
                .zoneOffset(weather.getZoneOffset())
                .build();
    }
}
