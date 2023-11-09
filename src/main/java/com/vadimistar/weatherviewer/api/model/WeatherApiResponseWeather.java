package com.vadimistar.weatherviewer.api.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherApiResponseWeather {
    String main;

    String icon;
}
