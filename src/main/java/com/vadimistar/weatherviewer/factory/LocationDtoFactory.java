package com.vadimistar.weatherviewer.factory;

import com.vadimistar.weatherviewer.dto.LocationDto;
import com.vadimistar.weatherviewer.entity.LocationEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoFactory {
    public LocationDto createLocationDto(LocationEntity entity) {
        return LocationDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
