package com.vadimistar.weatherviewer.domain.api;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiResponse {
    WeatherApiResponseMain main;

    List<WeatherApiResponseWeather> weather;

    Long dt;

    Long timezone;
}
