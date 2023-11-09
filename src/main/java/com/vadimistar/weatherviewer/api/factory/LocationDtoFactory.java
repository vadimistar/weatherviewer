package com.vadimistar.weatherviewer.api.factory;

import com.vadimistar.weatherviewer.api.dto.LocationDto;
import com.vadimistar.weatherviewer.api.dto.WeatherDto;
import com.vadimistar.weatherviewer.store.entity.LocationEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LocationDtoFactory {
    public LocationDto createLocationDto(LocationEntity location, WeatherDto weather) {
        return LocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .weather(weather.getWeather())
                .temperature(BigDecimal.valueOf(weather.getTemperature()))
                .build();
    }
}
