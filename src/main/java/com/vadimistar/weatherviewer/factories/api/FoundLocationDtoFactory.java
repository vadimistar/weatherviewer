package com.vadimistar.weatherviewer.factories.api;

import com.vadimistar.weatherviewer.dto.api.FoundLocationDto;
import com.vadimistar.weatherviewer.domain.api.GeocodingApiResponse;
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
