package com.vadimistar.weatherviewer.api.factory;

import com.vadimistar.weatherviewer.api.dto.FoundLocationDto;
import com.vadimistar.weatherviewer.api.model.GeocodingApiResponse;
import org.springframework.stereotype.Component;

@Component
public class FoundLocationDtoFactory {
    public FoundLocationDto createFoundLocationDto(GeocodingApiResponse apiResponse) {
        return FoundLocationDto.builder()
                .name(apiResponse.getName())
                .lat(apiResponse.getLat())
                .lon(apiResponse.getLon())
                .build();
    }
}
