package com.vadimistar.weatherviewer.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherApiResponse {
    WeatherApiResponseMain main;

    List<WeatherApiResponseWeather> weather;

    Long dt;
}
