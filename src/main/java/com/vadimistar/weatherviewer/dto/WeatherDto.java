package com.vadimistar.weatherviewer.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherDto {
    Instant time;

    Double temperature;

    String weather;

    String icon;
}
