package com.vadimistar.weatherviewer.dto.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.ZoneOffset;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherDto {
    Double temperature;

    String weather;

    String iconUrl;

    ZoneOffset zoneOffset;
}
