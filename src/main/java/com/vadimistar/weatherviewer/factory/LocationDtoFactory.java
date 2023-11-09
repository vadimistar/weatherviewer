package com.vadimistar.weatherviewer.factory;

import com.vadimistar.weatherviewer.dto.LocationDto;
import com.vadimistar.weatherviewer.dto.WeatherDto;
import com.vadimistar.weatherviewer.entity.LocationEntity;
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
