package com.vadimistar.weatherviewer.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.TimeZone;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherDto {
    Double temperature;

    String weather;

    String iconUrl;

    ZoneOffset zoneOffset;
}
